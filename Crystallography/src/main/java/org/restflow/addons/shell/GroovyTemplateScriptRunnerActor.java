package org.restflow.addons.shell;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;

import ssrl.workflow.actors.Actor.ActorFSM;
import ssrl.workflow.beans.TextScanner;
import ssrl.workflow.actors.AbstractActor;

/**
 * This class is thread safe.  Its superclass is thread safe, and all the mutable fields 
 * it adds are synchronized on the instance.  
 * 
 * The template is a special input needed by the actor; the key to this input is defined
 * in the static definition below  All other inputs defined by the workflow enter the
 * model and are available to the template engine.
 */

public class GroovyTemplateScriptRunnerActor extends AbstractActor implements BeanNameAware,
																  InitializingBean {
	public static final String TEMPLATE_INPUTKEY = "_template";
	public static final String OUTPUTKEY = "view";
	
	protected Log logger;

    
	public GroovyTemplateScriptRunnerActor() {
		super();
		
		synchronized(this) {
			logger = LogFactory.getLog(getClass());
		}
	}
	
	@Override
	public synchronized Object clone() throws CloneNotSupportedException {
		GroovyTemplateScriptRunnerActor theClone = (GroovyTemplateScriptRunnerActor) super.clone();
		theClone.logger = LogFactory.getLog(getClass());
		return theClone;
	}
	
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		_state = ActorFSM.PROPERTIES_SET;
	}
	
	public void elaborate() throws Exception {
		super.elaborate();
		_state = ActorFSM.ELABORATED;
	}
	
	public synchronized void configure() throws Exception {
		super.configure();		
		_state = ActorFSM.CONFIGURED;
	}
	
	public synchronized void initialize() throws Exception {
		super.initialize();
		_state = ActorFSM.INITIALIZED;		
	}
	
	@Override
	public synchronized void step() throws Exception {
		
		super.step();

		//for (String in : _constants.keySet() ) {
        //    System.out.println(in);
        //}
		String runFileTemplate = (String)_constants.get("_runFileTemplate");
		String envScript = (String)_constants.get("_envScript");
		String runDir = (String)_constants.get("_runDir");
		Map<String,Map<String,Object>> fileOutputMap = (Map<String,Map<String,Object>>)_constants.get("_outputFiles");
		Map<String,Map<String,String>> fileBuilderMap = (Map<String,Map<String,String>>)_constants.get("_fileBuilders");


        if (runFileTemplate == null) {
            throw new Exception("must set _runFileTemplate property");
        }
        if (envScript == null) {
            throw new Exception("must set _envScript property");
        }
        if (fileOutputMap == null) {
            throw new Exception("must set _outputFiles property");
        }
		
        if ( _actorStatus.getStepDirectory() == null) {
            throw new Exception("must use step directory");
        }
        String stepDir;
        if (runDir == null) {
            stepDir = _actorStatus.getStepDirectory().getPath();
        } else {
            stepDir = _actorStatus.getStepDirectory().getPath() + "/" + runDir;
        }

        System.out.println(runDir);
        System.out.println(stepDir);


        if ( fileBuilderMap != null ) { 
	        buildInputFiles( stepDir, fileBuilderMap );
        }

		File envScriptFile = new File( stepDir + "/env.csh");
		FileUtils.writeStringToFile(envScriptFile,envScript);
	    Process p = runCshFile(stepDir, envScriptFile, null);
	    String envString = IOUtils.toString(p.getInputStream(), "UTF-8");
	    
	    Map<String, String> envMap = convertEnvironmentStringToMap(envString);
	    
	    File runFile = createRunFileFromTemplate(stepDir, runFileTemplate);
	    Process p2 = runCshFile(stepDir, runFile,envMap);
	    
	    String stdoutText = IOUtils.toString(p2.getInputStream(), "UTF-8");
	    String stderrText = IOUtils.toString(p2.getErrorStream(), "UTF-8");	    

	    FileUtils.writeStringToFile(new File(stepDir + "/stdout.txt"),stdoutText);
	    FileUtils.writeStringToFile(new File(stepDir + "/stderr.txt"),stderrText);	    
	 
	    for ( Map.Entry<String, Map<String,Object>> entry : fileOutputMap.entrySet()  ) {
	    	String fileName = entry.getKey();
	    	File f = new File( _actorStatus.getStepDirectory().getPath() + "/" + fileName);
	    	Map<String,Object> outfileParameters = (Map<String,Object>)entry.getValue();
	    	String outputVar = (String)outfileParameters.get("output");
	    	Boolean mustExist = (Boolean)outfileParameters.get("mustExist");
	    	if (outputVar == null) throw new Exception("_runFileTemplate entry '"+ fileName +" requires an 'output' parameter");
	        
   	        if (! _outputSignature.containsKey(outputVar)) {
                throw new Exception( "'" + outputVar + "' is not a output of actor"); 
            }	

            if (f.exists()) {	
	    	    _outputValues.put(outputVar, f);
            } else {
                if (mustExist != null && mustExist.booleanValue() == false )  {
	    	        _outputValues.put(outputVar, null);
                } else {
                    throw new Exception( fileName + " does not exist.");
                }
            }	    	

	    	TextScanner scanner = (TextScanner)outfileParameters.get("parser");
	    	if ( scanner == null) continue;
	    	scanner.compile();
            _outputValues.putAll(scanner.search(FileUtils.readFileToString(f,"UTF-8")));
	    }
		//_outputValues.put( OUTPUTKEY, view);
		
		_state = ActorFSM.STEPPED;
	}


	private void buildInputFiles( String stepDir, Map<String,Map<String,String>> fileBuilderMap ) throws Exception {
	    for ( Map.Entry<String, Map<String,String>> entry : fileBuilderMap.entrySet()  ) {
	    	String fileName = entry.getKey();
	    	File f = new File(stepDir + "/" + fileName);
            
	    	Map<String,String> fileBuilderParameters = (Map<String,String>)entry.getValue();
            String template = fileBuilderParameters.get("template");
            
		    SimpleTemplateEngine engine = new SimpleTemplateEngine();
            Writable template1 = engine.createTemplate( template ).make( _inputValues );
		    String contents = template1.toString();
		    FileUtils.writeStringToFile(f,contents);
        }        

    }


	//converts the output of the shell env command to a map of strings
	static private Map<String, String> convertEnvironmentStringToMap(String envString) {
		String[]  env = envString.split("\n");
	    Map<String,String> envMap = new HashMap<String,String>();
	    for (int i = 0; i < env.length; i++) {
	    	int splitIndex = env[i].indexOf("=");
	    	if (splitIndex == -1) continue;
	    	String var = env[i].substring(0,splitIndex);
	    	String val = env[i].substring(splitIndex + 1);
	    	envMap.put(var, val);
	    }
		return envMap;
	}

	private Process runCshFile(String stepDir, File runFile,Map<String,String> env) throws IOException {
		runFile.setExecutable(true);
		List<String> scriptCmd = new Vector<String>();
		scriptCmd.add("tcsh");
		scriptCmd.add("-c");
		scriptCmd.add(runFile.toString());
	    
	    ProcessBuilder pb = new ProcessBuilder(scriptCmd).directory( new File(stepDir)) ;
	    if (env != null) {
	    	pb.environment().putAll(env);
	    }
	    Process p = pb.start();
	    return p;
	}
	
	private File createRunFileFromTemplate(String stepDir, String template) throws Exception {
		Map<String,Object> model = new HashMap<String, Object>();
		for ( String key : _inputSignature.keySet() ) {
			model.put(key, _inputValues.get(key) );
		}
		bindSpecial(model);
		bindConstants(model);
		
		SimpleTemplateEngine engine = new SimpleTemplateEngine();
		Writable template1 = engine.createTemplate( template ).make( model  );
		String view = template1.toString();

		File runFile = new File( stepDir + "/run.csh");
		FileUtils.writeStringToFile(runFile,view);
		return runFile;
	}
	
	public synchronized void wrapup() throws Exception {	
		super.wrapup();
		_state = ActorFSM.WRAPPED_UP;
	}
	
	public synchronized void dispose() throws Exception {
		super.dispose();
		_state = ActorFSM.DISPOSED;
	}
	
	private synchronized void bindSpecial(Map<String,Object> binding) {
		binding.put("_inputs", _inputValues);
		binding.put("_states", _stateVariables);
		binding.put("_outputs", _outputSignature.keySet());
		binding.put("_status" , _actorStatus);
		binding.put("_type", _signatureElementTypes);	
		binding.put("_this" , this);
		binding.put("STEP", _actorStatus.getStepCount());
	}
	

	private synchronized void bindConstants(Map<String,Object> model) {
		for (String name: _constants.keySet()) {
			Object value = _constants.get(name);
			model.put(name,value);
		}
	}

	
}
