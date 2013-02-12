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

public class TestStrategy extends TestCase {
	
	public void testScoreSample() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");

		String file =  "classpath:org/restflow/addons/Strategy/workflows/TestStrategy.yaml";		
        String workflow = "Strategy.Test.Workflow";

		String image1 = new File("target/test-classes/org/restflow/addons/Strategy/inputs/1/B2_0001.cbf").getAbsolutePath();
		String image2 = new File("target/test-classes/org/restflow/addons/Strategy/inputs/1/B2_0002.cbf").getAbsolutePath();
        String validatePath = "target/test-classes/org/restflow/addons/Strategy/inputs/1/output.yaml";  
		String infile = "target/test-classes/org/restflow/addons/Strategy/inputs/1/infile.yaml";

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "Strategy" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow,"-i", "image1="+ image1,"-i","image2="+image2, "-i", "validatePath=" + validatePath, "-infile", infile });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
