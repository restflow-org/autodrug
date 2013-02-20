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

public class TestPointless extends TestCase {
	
	public void testPointless1() throws Exception {

		String file =  "classpath:org/restflow/addons/pointless/workflows/TestPointless.yaml";		
        String workflow = "pointless.Test.Workflow";

		String xdsascii = "target/test-classes/org/restflow/addons/pointless/inputs/1/XDS_ASCII.HKL";
        String validatePath = "target/test-classes/org/restflow/addons/pointless/outputs/1/out.yaml";  

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "Pointless1" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-i", "xdsascii="+ xdsascii, "-i", "validatePath=" + validatePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}
	public void testPointless2() throws Exception {

		String file =  "classpath:org/restflow/addons/pointless/workflows/TestPointless.yaml";		
        String workflow = "pointless.Test.Workflow";

		String xdsascii = "target/test-classes/org/restflow/addons/pointless/inputs/2/XDS_ASCII.HKL";
        String validatePath = "target/test-classes/org/restflow/addons/pointless/outputs/2/out.yaml";  

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "Pointless2" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-i", "xdsascii="+ xdsascii, "-i", "validatePath=" + validatePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
