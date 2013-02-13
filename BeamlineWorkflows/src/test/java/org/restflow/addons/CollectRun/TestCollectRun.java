package org.restflow.addons.CollectRun;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.restflow.WorkflowContext;


import joptsimple.OptionSet;

import junit.framework.TestCase;

import org.restflow.RestFlow;
import org.restflow.WorkflowContext;
import org.restflow.util.PortableIO;
import org.restflow.util.StdoutRecorder;
import org.restflow.util.TestUtilities;

public class TestCollectRun extends TestCase {
	
	public void testCollectRun() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");
		
		String infile = "target/test-classes/org/restflow/addons/CollectRun/inputs/1/infile.yaml";
        String file =  "classpath:org/restflow/addons/CollectRun/workflows/TestCollectRun-OptionList.yaml";
        String testDataMap = "target/test-classes/org/restflow/addons/CollectRun/inputs/1/testDataMap.yaml";
        
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "CollectRun" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "CollectRun.Test.Workflow", "-infile", infile, "-i", "testDataDir="+ workspace,"-i","testDataImageLookupMapPath="+ testDataMap});
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
