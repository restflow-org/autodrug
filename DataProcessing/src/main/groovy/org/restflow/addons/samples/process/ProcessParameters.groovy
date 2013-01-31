package org.restflow.addons.samples.process;

class ProcessParameters {
	Float intensity_cutoff_sigma;
	String space_group_number;
	String unit_cell_angstroms_degrees;
	Float resolution_angstroms;
	
	public void validate () {
			
		if (this.space_group_number == null ) {
			this.space_group_number = "0";
		}

		if (this.unit_cell_angstroms_degrees == null ) {
			this.unit_cell_angstroms_degrees = "0 0 0 0 0 0";
		}

	}
	
}
