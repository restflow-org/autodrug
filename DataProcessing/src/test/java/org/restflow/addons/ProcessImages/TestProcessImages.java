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

public class TestProcessImages extends TestCase {
	
	public void testProcessImagesCbf () throws Exception {
		String testData = System.getenv("TEST_DATA");
		
		if (testData == null) throw new Exception("must set TEST_DATA environment variable");
		
		String infile = "target/test-classes/org/restflow/addons/ProcessImages/inputs/1/infile.yaml";
        String file =  "classpath:org/restflow/addons/ProcessImages/workflows/TestProcessImages-A.yaml";
        String firstImagePath = new File(testData + "/TestAddons/TestDataProcessing/1/B1_1_00001.cbf").getAbsolutePath();
        String mrModel = "target/test-classes/org/restflow/addons/ProcessImages/inputs/1/2MBW.pdb";
        
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "ProcessImages" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "ProcessImages.Test.Workflow", "-infile", infile, "-i", "firstImagePath="+ firstImagePath,"-i","mr_model="+ mrModel});
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

	public void testProcessImagesMccd () throws Exception {
		String testData = System.getenv("TEST_DATA");
		
		if (testData == null) throw new Exception("must set TEST_DATA environment variable");
		
		String infile = "target/test-classes/org/restflow/addons/ProcessImages/inputs/1/infile.yaml";
        String file =  "classpath:org/restflow/addons/ProcessImages/workflows/TestProcessImages-A.yaml";
        String firstImagePath = new File(testData + "/TestAddons/TestDataProcessing/2/A2_12_00001.mccd").getAbsolutePath();
        String mrModel = "target/test-classes/org/restflow/addons/ProcessImages/inputs/2/2MBW.pdb";
        
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "ProcessImages" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "ProcessImages.Test.Workflow", "-infile", infile, "-i", "firstImagePath="+ firstImagePath,"-i","mr_model="+ mrModel});
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}




}
