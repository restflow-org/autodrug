package org.restflow.addons.ScreenSample;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.restflow.RestFlow;
import org.restflow.WorkflowContext;


import joptsimple.OptionSet;

import junit.framework.TestCase;

import org.restflow.WorkflowContext;
import org.restflow.util.PortableIO;
import org.restflow.util.StdoutRecorder;
import org.restflow.util.TestUtilities;

public class TestScreenSample extends TestCase {
	
	public void testScreenSample() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");
		
		String infile = "target/test-classes/org/restflow/addons/ScreenSample/inputs/1/infile.yaml";
        String file =  "classpath:org/restflow/addons/ScreenSample/workflows/TestScreenSample-OptionList-Sim.yaml";
        //String testDataDir = new File(workspace + "/1/B1_1_00001.cbf").getAbsolutePath();
        String testDataMap = "target/test-classes/org/restflow/addons/ScreenSample/inputs/1/testDataMap.yaml";
        
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "ScreenSample" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "ScreenSample.Test.Workflow", "-infile", infile, "-i", "testDataDir="+ workspace,"-i","testDataImageLookupMapPath="+ testDataMap});
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
