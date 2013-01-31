package edu.stanford.slac.smb.samples;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;


//top level data structure for autodrug input file.
public class ProjectList {
	Map<String,ProteinProject> _projects = new LinkedHashMap<String,ProteinProject>();
	
	public ProteinProject addProject (String proteinProject) {
		ProteinProject existingGroup = _projects.get(proteinProject);
		if ( existingGroup != null ) return existingGroup;
			
		ProteinProject newRoster = new ProteinProject();
		_projects.put(proteinProject,newRoster);
		return newRoster;
	}
	
	public boolean empty () {
		return (_projects.size() == 0);
	}
	
	public String toString() {
		if (_projects == null ) return null;
		return _projects.keySet().toString();
	}

	public Map<String, ProteinProject> getProteinProjects() {
		return _projects;
	}

	public void setProteinProjects(TreeMap<String, ProteinProject> projects) {
		_projects = projects;
	}
	
	public void alphabetize() {
		TreeMap<String,ProteinProject> alphabetizedProjects = new TreeMap<String,ProteinProject>();
		alphabetizedProjects.putAll(_projects);
		
		//sort each of the sample lists
		for ( Entry<String,ProteinProject> entry : alphabetizedProjects.entrySet() ) {
			entry.getValue().alphabetize();
		}
		
		_projects = alphabetizedProjects;
	}

	public void moveCurrentlyMountedProjectToFirstPosition(String silCassettePosition, String dewarPort){
		
		//sort each of the sample lists
		for ( Entry<String,ProteinProject> entry : _projects.entrySet() ) {
			if ( entry.getValue().moveCurrentlyMountedSampleToFirstPosition(silCassettePosition, dewarPort) ) {
				
				LinkedHashMap<String,ProteinProject> projectsWithMountedSampleFirst = new LinkedHashMap<String,ProteinProject>();
				projectsWithMountedSampleFirst.put(entry.getKey(),entry.getValue() );
				_projects.remove(entry.getKey());
				projectsWithMountedSampleFirst.putAll(_projects);
				_projects = projectsWithMountedSampleFirst;
				break;
			}
		}
		
	}
	
}
