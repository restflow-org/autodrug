package org.restflow.addons.samples.project;

import org.restflow.addons.samples.collect.*;
import org.restflow.addons.samples.screen.*;
import org.restflow.addons.samples.process.*;
import java.util.Map;

class ProteinSpecification {
	ScreenParameters screening_parameters;
	Map<String,SpaceGroupDependent> spacegroups;
    ProcessParameters processing_parameters;
    MolecularReplacementParameters  molecular_replacement;
	
	public void validate () {
		if (screening_parameters == null) throw new Exception("screening_parameters must be defined for group");
		if (spacegroups == null || spacegroups.size == 0) throw new Exception("at least one spacegroup must be defined for a group");
		if (molecular_replacement == null) throw new Exception("molecular_replacement must be defined for group");

        if ( processing_parameters == null ) {
            processing_parameters = new ProcessParameters();
        }
		
		screening_parameters.validate();
		spacegroups.each {key, value -> value.validate() }
		molecular_replacement.validate();
		processing_parameters.validate();

	}

    public Map<String,CollectCriteria> returnSpaceGroupCollectCriteria () {
        def sg2CollectionCriteria = [:]
        spacegroups.each {k,v -> 
            sg2CollectionCriteria[k]=v.collection_criteria;
        }
        return sg2CollectionCriteria;
    }

    public Map<String,CollectParameters> returnSpaceGroupCollectParameters () {
        def sg2CollectionParams = [:]
        spacegroups.each {k,v -> 
            sg2CollectionParams[k]=v.collection_parameters;
        }
        return sg2CollectionParams;
    }
}
