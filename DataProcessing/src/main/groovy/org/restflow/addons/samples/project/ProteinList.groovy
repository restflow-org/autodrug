package org.restflow.addons.samples.project;

import java.util.Map;

class ProteinList implements Serializable{
	Map<String,ProteinSpecification> groups;
	
	public void validate () {
		if (groups == null) throw new Exception("at least one group must be defined");
		groups.each { key,value-> 
			try {
				value.validate()
			} catch (Exception e) {
				throw new Exception("group '$key' has a problem: $e.message() ");
			};
		}
	}
}
