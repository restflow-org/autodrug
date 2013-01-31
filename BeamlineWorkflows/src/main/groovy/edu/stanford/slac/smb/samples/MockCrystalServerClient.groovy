package edu.stanford.slac.smb.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.Yaml;

import ssrl.beans.AppSession;
import sil.beans.Crystal;
import sil.beans.Sil;


class MockCrystalServerClient implements SampleManagerInterface {
	String crystalServerUrl;

	String sessionId;
	String userName;
    String sampledb;
    String sildb;
	
	public CrystalServerClient() {
	}
	
	public setAppSession (AppSession appSession) {
	}
	
	ArrayList<Crystal> listSamples( String silId ) {
		
		println silId

		Yaml yaml = new Yaml(new FilterConstructor());
		return (List) yaml.load(sampledb);
	}
	
	Sil readSil( String silId ) {
		
		println silId

		Yaml yaml = new Yaml(new SilBeanFilterConstructor());
		return yaml.load(sildb);
	}

	String beamlinePositionAssignedToSil( String beamlineId, String silId ) {
		return "m";
	}
		
	Map<String,String> listSpreadsheets() {
		return [];
	}
	

	def updateCrystal(String spreadsheetId, String uniqueId, Map properties) {
	}
}


