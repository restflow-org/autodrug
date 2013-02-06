package org.restflow.addons.samples.project;

import org.restflow.addons.samples.collect.*;

class SpaceGroupDependent implements Serializable {
	CollectCriteria collection_criteria;
	CollectParameters collection_parameters;
	
	public void validate () {
		['collection_criteria',
			'collection_parameters'].each { field ->
			if ( this."$field" == null ) {
				throw new Exception("$field must be defined in spacegroup");
			}
			this."$field".validate();
		}
	}

	
}
