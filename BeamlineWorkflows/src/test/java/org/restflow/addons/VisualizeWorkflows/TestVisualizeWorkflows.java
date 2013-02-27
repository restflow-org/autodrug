package org.restflow.addons.VisualizeWorkflows;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.restflow.WorkflowContext;


import joptsimple.OptionSet;

import junit.framework.TestCase;

import org.restflow.RestFlow;
import org.restflow.WorkflowContext;
import org.restflow.util.PortableIO;
import org.restflow.util.StdoutRecorder;
import org.restflow.util.TestUtilities;

public class TestVisualizeWorkflows extends TestCase {
	
	public void testVisualizeWorkflows() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");
		
        String file =  "classpath:org/restflow/addons/util/workflows/visualizeBeamlineWorkflows.yaml";
        
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "VisualizeWorkflows" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "BeamlineWorkflowsUtils.Workflow" });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}
	public void testVisualizeHierarchy() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
        String file =  "classpath:tools/hierarchy.yaml";
        
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "VisualizeHierarchy_" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w","Visualize.WorkflowHierarchy" ,"-i","restflowFile=classpath:org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml","-i","workflowName=AutoDrug.A.Workflow"  });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}







}
