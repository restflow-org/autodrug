package org.restflow.addons.samples.collect;

class CollectCriteria implements Serializable {
	Float priority;
	String unit_cell_angstroms_degrees;
	Float unit_cell_length_tolerance_percent;
	Float minimum_resolution_angstroms;
	Float maximum_mosaicity_degrees;
	Float detector_resolution_angstroms;
	
	public void validate () {
		['priority',
			'unit_cell_angstroms_degrees',
			'unit_cell_length_tolerance_percent',
			'minimum_resolution_angstroms',
			'maximum_mosaicity_degrees'].each { field->
			if ( this."$field" == null ) {
				throw new Exception("$field must be defined in collection_criteria");
			}
		}
		if (this.unit_cell_angstroms_degrees.tokenize().size()!=6) throw new Exception("need six parameters for 'unit_cell_angstroms_degrees'");
			
	}
}
