package org.restflow.addons.ProcessSample;
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

public class TestProcessSample extends TestCase {
	
	public void testProcessSample() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");
		
		String infile = "target/test-classes/org/restflow/addons/ProcessSample/inputs/1/infile.yaml";
        String file =  "classpath:org/restflow/addons/ProcessSample/workflows/TestProcessSample-OptionList.yaml";
        String firstImagePathDir = new File(workspace + "/TestCollect/cbf/B2/B2_1_00001.cbf").getAbsolutePath();
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "ProcessSample" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "ProcessSample.Test.Workflow", "-infile", infile, "-i", "firstImagePath="+ firstImagePathDir});
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
