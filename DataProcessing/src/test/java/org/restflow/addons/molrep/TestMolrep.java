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

public class TestMolrep extends TestCase {
	
	public void testMolrep() throws Exception {

		String file =  "classpath:org/restflow/addons/Molrep/workflows/Molrep.yaml";		
        String workflow = "Molrep.Workflow";

		String infile = "target/test-classes/org/restflow/addons/Molrep/inputs/1/infile.yaml";

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "Molrep" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow, "-infile", infile });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
