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

public class TestMolecularReplacement extends TestCase {
	
	public void testMolecularReplacement() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");

		String file =  "classpath:org/restflow/addons/MolecularReplacement/workflows/TestMolecularReplacement.yaml";		
        String workflow = "MolecularReplacement.Test.Workflow";

		String mtz = new File("target/test-classes/org/restflow/addons/MolecularReplacement/inputs/1/mr.mtz").getAbsolutePath();
		String pdb = new File("target/test-classes/org/restflow/addons/MolecularReplacement/inputs/1/mr.pdb").getAbsolutePath();

		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "MolecularReplacement" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", workflow,"-i", "mtz="+ mtz,"-i","pdb="+pdb, "-i", "refiType=REST", "-i", "numCycles=10","-i","rcutoff=0.4" });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
