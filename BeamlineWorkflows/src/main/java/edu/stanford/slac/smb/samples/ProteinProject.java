package edu.stanford.slac.smb.samples;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import sil.beans.Crystal;
import sil.beans.Sil;
import org.restflow.addons.samples.project.*;

// second layer of data structure for samples within a group
// first layer of data structure for samples not distinguished by group
public class ProteinProject {
	Map<String,MetaCrystal> samples = new LinkedHashMap<String,MetaCrystal>();
	
	// must keep entire spreadsheet with crystal info
	public MetaCrystal addSample ( Sil sil, Crystal crystal) {
		MetaCrystal sample = new MetaCrystal();
		sample.crystal = crystal;
		sample.sil = sil;
		
		//add port to metadata
		String cassettePosition = sil.getInfo().getBeamlinePosition();
        System.out.println (cassettePosition);
		String dewarPortPrefix = cassettePosition.substring(0, 1); //left turns to l, right turns to r, and middle turns to m
        System.out.println( dewarPortPrefix);
		sample.dewarPort = dewarPortPrefix + sample.getPort();

		//String crystalId = sample.getCrystalId();
		samples.put(crystal.getCrystalId(), sample);
		
		return sample;
	}
	
	public void addStrategy ( ProteinSpecification strategy) {
		for (Entry<String,MetaCrystal> sample : samples.entrySet() ) {
			sample.getValue().setStrategy(strategy);
		}
	}
	
	
	public String toString() {
		if (samples == null ) return null;
		return samples.keySet().toString();
	}
	
	public boolean empty () {
		return (samples.size() == 0);
	}
	
	
	public void alphabetize() {
		TreeMap<String,MetaCrystal> alphabetizedSamples = new TreeMap<String,MetaCrystal>();
		alphabetizedSamples.putAll(samples);
		
		samples = alphabetizedSamples;
	}	

	public boolean moveCurrentlyMountedSampleToFirstPosition(String silCassettePosition, String dewarPort){
		
		//sort each of the sample lists
		for ( Entry<String,MetaCrystal> entry : samples.entrySet() ) {
			//String silDewarPosition = silCassettePosition + entry.getValue().getDewarPort();
			if ( entry.getValue().getDewarPort().compareTo( dewarPort ) == 0  ) {
				LinkedHashMap<String,MetaCrystal> projectWithMountedSampleFirst = new LinkedHashMap<String,MetaCrystal>();
				projectWithMountedSampleFirst.put(entry.getKey(),entry.getValue() );
				samples.remove(entry.getKey());
				projectWithMountedSampleFirst.putAll(samples);
				samples = projectWithMountedSampleFirst;
				return true;
			}
		}
		return false;
	}	
	
}
