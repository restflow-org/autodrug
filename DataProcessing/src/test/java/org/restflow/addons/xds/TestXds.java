package org.restflow;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.restflow.WorkflowContext;


import joptsimple.OptionSet;

import junit.framework.TestCase;

import org.restflow.WorkflowContext;
import org.restflow.util.PortableIO;
import org.restflow.util.StdoutRecorder;
import org.restflow.util.TestUtilities;

public class TestXds extends TestCase {
	

	public void testXds() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");

		String file =  "classpath:org/restflow/addons/xds/workflows/TestXds.yaml";		
        String workflow = "xds.Test.Workflow";

		String infile = "target/test-classes/org/restflow/addons/xds/inputs/1/in.yaml";

		String firstImagePath = workspace + "/TestAddons/TestDataProcessing/1/B1_1_00001.cbf";
        String mrModel = "target/test-classes/org/restflow/addons/ProcessImages/inputs/2/2MBW.pdb";
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "XDS" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-infile", infile, "-i", "firstImagePath="+ firstImagePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

/*
	public void testXdsParser() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");

		String file =  "classpath:org/restflow/addons/xds/workflows/testXdsParser.yaml";		
        String workflow = "xds.TestParser.Workflow";

		String xdsStdOutPath = "target/test-classes/org/restflow/addons/xds/parser/1/stdout.txt";
        String validatePath = "target/test-classes/org/restflow/addons/xds/parser/1/parser-out.yaml";  

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "xds-parser_" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow,"-i", "xdsStdoutPath="+ xdsStdOutPath, "-i", "validatePath=" + validatePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}
*/
}
