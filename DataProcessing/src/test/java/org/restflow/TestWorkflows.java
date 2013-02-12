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

public class TestWorkflows extends TestCase {
	
	public void testDataProcessing() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");

		String file =  "classpath:org/restflow/addons/DataProcessing/workflows/DataProcessingTest-P.yaml";		
        String workflow = "DataProcessing.Test.Workflow";

		String infile = "src/test/resources/org/restflow/addons/DataProcessing/inputs/1/DataProcessing.in";

        String firstImagePath = new File(workspace + "/1/B1_1_00001.cbf").getAbsolutePath();

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "DataProcessing" + PortableIO.createTimeStampString();

		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-infile", infile, "-i", "firstImagePath="+ firstImagePath });
		System.out.println(runDir);
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
