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

public class TestLabelit extends TestCase {
	
	
	public void testLabelit() throws Exception {

		String file =  "classpath:org/restflow/addons/labelit/workflows/TestLabelit.yaml";		
        String workflow = "labelit.Test.Workflow";

		String image1 = new File("target/test-classes/org/restflow/addons/labelit/inputs/1/B2_0001.cbf").getAbsolutePath();;
		String image2 = new File("target/test-classes/org/restflow/addons/labelit/inputs/1/B2_0002.cbf").getAbsolutePath();
        String validatePath = "target/test-classes/org/restflow/addons/labelit/inputs/1/output.yaml";  

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "labelit" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow,"-i", "image1="+ image1,"-i","image2="+image2, "-i", "validatePath=" + validatePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
