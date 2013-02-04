package org.restflow.addons.AutoDrug;

import junit.framework.TestCase;

public class TestAutoDrug extends TestCase {

	public void test() throws Exception {
		String WORKSPACE = "/home/scottm/workspace-restflow/";
		String PROJECT = WORKSPACE + "BeamlineWorkflows/";		
		String TEST_DIR = "/data/scottm/TestRestFlow";
		String workflowFile = PROJECT + "src/test/workflows/org/restflow/addons/AutoDrug/workflows/TestAutoDrug-OptionList.yaml";
		
        String base = PROJECT + "src/test/resources/org/restflow/addons/AutoDrug/inputs/1/infile.yaml";
        String strategy = PROJECT + "src/test/resources/org/restflow/addons/AutoDrug/inputs/1/proteins.yaml";
        String testDataMap = PROJECT + "/src/test/resources/org/restflow/addons/AutoDrug/inputs/1/testDataMap.yaml";        

        org.restflow.RestFlow.main(new String[]{"-base",base,"-f", workflowFile,
        		"-w","AutoDrug.Test.Workflow",
        		"-i","strategy-file="+ strategy,
        		"-i","testDataDir="+ TEST_DIR,
        		"-i","testDataImageLookupMapPath="+testDataMap});
	}

}
