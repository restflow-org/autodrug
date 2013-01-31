package edu.stanford.slac.smb.samples;
 
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import sil.beans.Crystal;
import sil.beans.Sil;

import edu.stanford.slac.smb.samples.*;

class SpreadsheetUtils {

	SampleManagerInterface sampleInfoManager;
	
	public ProjectList sortSilByProtein(String spreadsheetId) {
		
		def sil = sampleInfoManager.readSil ( spreadsheetId );
		
		def crystals = sil.crystals;
						
		//LinkedHashMap<String, Map<String,MetaCrystal>> groups = new LinkedHashMap<String, Map<String,MetaCrystal>>();
		ProjectList projectList = new ProjectList();
		
		crystals.each { key,crystal ->
		  if (crystal.selected == false) return; //don't screen unselected samples
		  def groupKey = crystal.data.protein;
		  if (groupKey == null || groupKey == "" ) return;  //only screen samples with protein column
		  		  
		  ProteinProject proteinProject = projectList.addProject (groupKey);		  
		  proteinProject.addSample(sil,crystal);
		}
		
		if ( projectList.empty() ) throw new Exception("No samples selected for screening in spreadsheet.")
		
		return projectList;
	}
		
	// read spreadsheet for samples
	public ProteinProject readSilSamples(String spreadsheetId) {
		
		def sil = sampleInfoManager.readSil (spreadsheetId);
		def crystals = sil.crystals;
		
		ProteinProject samples = new ProteinProject();
		
		crystals.each{ key,crystal ->
			if (crystal.selected == false) return; // only screen selected samples
			
			samples.addSample (sil,crystal);
			
		}
		
		if (samples.empty()) throw new Exception("No samples selected for screening in spreadsheet.")
		
		return samples;
	}
	
	public Object updateCrystal(String spreadsheetId, String uniqueId,
			Map properties) {
		return sampleInfoManager.updateCrystal(spreadsheetId, uniqueId, properties);
	}
			
	public beamlinePositionAssignedToSil (String beamlineId, String silId) {
		return sampleInfoManager.beamlinePositionAssignedToSil(beamlineId, silId);
	}
	
/*
	public Map<String,Map<String,MetaCrystal>> sortCrystalsByGroup(String spreadsheetId) {
		
		def samples = listSamples( spreadsheetId );
		
		LinkedHashMap<String, Map<String,MetaCrystal>> groups = new LinkedHashMap<String, Map<String,MetaCrystal>>();
		
		samples.each {
		  if (it.selected == false) return; //don't screen unselected samples
		  def group = it.data.protein;
		  if (group == null || group == "" ) return;  //only screen samples with protein column
		  
		  def crystalId = it.crystalId;
		  if ( groups[group] == null ) {
			groups[group] = new LinkedHashMap<String,MetaCrystal>();
		  }
		  def crystal = new MetaCrystal();
		  crystal.crystal = it;
		  crystal.spreadsheetId = spreadsheetId;
		  groups[group][crystalId] = crystal;
		}
		
		return groups;
	} 
*/
	
}
