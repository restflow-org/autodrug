package org.restflow.addons.AutoDrug;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.restflow.RestFlow;
import org.restflow.WorkflowContext;


import joptsimple.OptionSet;

import junit.framework.TestCase;

import org.restflow.WorkflowContext;
import org.restflow.util.PortableIO;
import org.restflow.util.StdoutRecorder;
import org.restflow.util.TestUtilities;

public class TestAutoDrugValidateInput extends TestCase {
	
	public void testValidateInput() throws Exception {
		String workspace = System.getenv("TEST_DATA");
		
		if (workspace == null) throw new Exception("must set TEST_DATA environment variable");
		
		String infile = "target/test-classes/org/restflow/addons/AutoDrug/inputs/1/infile.yaml";
        String file =  "classpath:org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml";
        String strategyFile = "target/test-classes/org/restflow/addons/AutoDrug/inputs/1/proteins.yaml";
        
        //String runsDir = RestFlow.buildRunDirectoryContainer(new OptionSet());
		
		String runsDir = WorkflowContext.getUserTemporaryDirectoryPath() + "/testruns/";
		String runDir = "AutoDrugValidateInput" + PortableIO.createTimeStampString();

		System.out.println(runDir);
		RestFlow.main( new String[] {"-base",runsDir, "-run" ,runDir,"-f",file,"-w", "AutoDrug-ValidateInput.Test.Workflow", "-infile", infile, "-i", "strategy-file="+ strategyFile });
		
		if ( new File(runsDir+runDir+"/_metadata/stderr.out").length() > 0) {
			throw new Exception("stderr is not empty");
		}
	}

}
