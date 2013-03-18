package edu.stanford.slac.smb.samples;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import groovyx.net.http.HTTPBuilder;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.Yaml;

import sil.beans.Crystal;
import sil.beans.Sil;
import ssrl.beans.AppSession;
import org.restflow.modelgrep.ModelGrep;
import org.restflow.modelgrep.RegularExpressionWithModel;
import edu.stanford.slac.smb.samples.*;

import static groovyx.net.http.Method.*
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*

class CrystalServerClient implements SampleManagerInterface {
	String crystalServerUrl;

	String sessionId;
	String userName;
	
	public CrystalServerClient() {
		super();
		setAppSession( CachedSessionIdGrabber.grabSession() );
	}
	
	public setAppSession (AppSession appSession) {
		def authSession = appSession.authSession
		sessionId = authSession.sessionId
		userName = authSession.userName
	}
	
    //listSamples not used?
	ArrayList<Crystal> listSamples( String silId ) {
		
		println silId

		def http = new HTTPBuilder  ( "${crystalServerUrl}")
		
		http.handler.failure = { resp ->
			throw new Exception ("Unexpected failure: ${resp.statusLine}")
		}
		
		def query = [silId: silId, userName: userName, SMBSessionID: sessionId ]
		

		List samples;		
		def html = http.get(path: "getCrystalList.do", contentType: TEXT, query: query ) { resp, reader ->
			def text = reader.text
			//println text
			Yaml yaml = new Yaml(new FilterConstructor());
			samples = (List) yaml.load(text);
		}
		return samples
	}
	
	Sil readSil( String silId ) {
		
		println silId

		def http = new HTTPBuilder  ( "${crystalServerUrl}")
		
		http.handler.failure = { resp ->
			throw new Exception ("Unexpected failure: ${resp.statusLine}")
		}
		
		def query = [silId: silId, userName: userName, SMBSessionID: sessionId, format: "yaml" ]
		

		Sil sil;
		def html = http.get(path: "getSilBean.do", contentType: TEXT, query: query ) { resp, reader ->
			def text = reader.text
			//println text
			Yaml yaml = new Yaml(new SilBeanFilterConstructor());
			sil = yaml.load(text);
		}
		return sil;
	}

	String beamlinePositionAssignedToSil( String beamlineId, String silId ) {

		

		def assigned = ''; 
		def positions = [ '1':'l','2':'m','3':'r'];
		positions.each { positionIndex, positionLabel -> 
			Sil sil;
			def query = [silId: silId, userName: userName, SMBSessionID: sessionId, beamline: beamlineId, forCassetteIndex: positionIndex ]
			try {
                def http = new HTTPBuilder  ( "${crystalServerUrl}")
		        println ("url ${crystalServerUrl} query: $query");	

		        http.handler.failure = { resp ->
			        throw new Exception ("Unexpected failure: ${resp.statusLine}")
                }

                def html = http.get(path: "getCrystalData.do", contentType: TEXT, query: query ) { resp, reader ->
					def text = reader.text
					//println text

					ModelGrep  s = new ModelGrep();
					s.setTokenMatcherPrefix("[");
					s.setTokenMatcherSuffix("]");
					s.setAbsorbWhiteSpaceSymbol("_");
			
					List<String> params = new Vector<String>();
					params.add("_{_{[spreadsheet:INT]}_{[:INT]} {load}");
					s.setTemplate(params);
					s.compile();
					Map<String, Object> result = s.search(text);
			
					String assignedSil = result.get("spreadsheet").toString();
					if ( assignedSil.compareTo(silId.toString()) != 0 ) {
						println ("Skipping wrong spreadsheet '"+ assignedSil + "' at at positionIndex: " +positionIndex);
						return;
					}
					println ("Matched spreadsheet '"+ assignedSil + "' at positionIndex: " +positionIndex);
					assigned = positionLabel;
					return;
                }
			//Yaml yaml = new Yaml(new SilBeanFilterConstructor());
			//sil = yaml.load(text);
			} catch (Exception e) {
				println ("Could not read SIL information at positionIndex:" +positionIndex + " :" + e.message);
			};
		}
		return assigned;
	}
		
	Map<String,String> listSpreadsheets() {
		def http = new HTTPBuilder()  ( "${crystalServerUrl}" )
		
		http.handler.failure = { resp ->
			throw new Exception ("Unexpected failure: ${resp.statusLine}")
		}
		
		def query = [userName: userName, SMBSessionID: sessionId, format: 'yaml' ]

		Map spreadsheets;
		def html = http.get(path: "getSilList.do", contentType: TEXT, query: query ) { resp, reader ->
			Yaml yaml = new Yaml(new FilterConstructor());
			spreadsheets = yaml.load(reader.text);
		}
		return spreadsheets
	}
	

	def updateCrystal(String spreadsheetId, String uniqueId, Map properties) {
		def http = new HTTPBuilder  ( "${crystalServerUrl}" )
		
		http.handler.failure = { resp ->
			throw new Exception ("Unexpected failure: ${resp.statusLine}")
		}

		def query = [userName: userName, SMBSessionID: sessionId, uniqueId: uniqueId, silId: spreadsheetId]
		query.putAll(properties)
		
		def html = http.get(path: "setCrystal.do", contentType: TEXT, query: query ) { resp, reader ->
			println reader.text;
		}
	}
}

//not used?
class FilterConstructor extends Constructor {
	
	@Override
	protected Class<?> getClassForName(String name) throws ClassNotFoundException {
		if ( name != "sil.beans.SilInfo" && name != "sil.beans.Crystal") {
			throw new RuntimeException("Filter is applied.");
		}
		return HashMap.class
	}
}
	
class SilBeanFilterConstructor extends Constructor {
	
	@Override
	protected Class<?> getClassForName(String name) throws ClassNotFoundException {
		if ( name != "sil.beans.Sil" ) {
			throw new RuntimeException("Expecting a Sil bean.");	
		}
		return Sil.class;
	}
}

