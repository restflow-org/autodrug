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

public class TestRaddose extends TestCase {
	
	public void testRaddose() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");

		String file =  "classpath:org/restflow/addons/raddose/workflows/raddoseTest.yaml";		
        String workflow = "raddose.Test.Workflow";

		String infile = "target/test-classes/org/restflow/addons/raddose/inputs/1/infile.yaml";

        String validatePath = "target/test-classes/org/restflow/addons/raddose/inputs/1/output.yaml";  

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "Raddose" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-infile", infile, "-i", "validatePath=" + validatePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
