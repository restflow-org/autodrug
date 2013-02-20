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

public class TestGetImgHeader extends TestCase {
	
	

	public void testGetImageHeader() throws Exception {

		String file =  "classpath:org/restflow/addons/getImgHeader/workflows/TestGetImgHeader.yaml";		
        String workflow = "getImgHeader.Test.Workflow";

		String imagePath = new File( "target/test-classes/org/restflow/addons/getImgHeader/inputs/1/B1_1_00001.cbf").getAbsolutePath();
        String validatePath = "target/test-classes/org/restflow/addons/getImgHeader/inputs/1/header.yaml";  

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "getImgHeader" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow,"-i", "imagePath="+ imagePath, "-i", "validatePath=" + validatePath });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}
}
