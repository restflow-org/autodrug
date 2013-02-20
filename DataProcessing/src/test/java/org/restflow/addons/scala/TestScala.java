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

public class TestScala extends TestCase {
	
	public void testScala() throws Exception {

		String file =  "classpath:org/restflow/addons/scala/workflows/TestScala.yaml";		
        String workflow = "scala.Test.Workflow";

        String mtz = "target/test-classes/org/restflow/addons/scala/inputs/1/point1.mtz";
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "scala" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-i", "mtz="+mtz,"-i", "isigmaCutoff=1.0" });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
