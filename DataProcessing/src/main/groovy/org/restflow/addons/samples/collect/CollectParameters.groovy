package org.restflow.addons.samples.collect;

class CollectParameters implements Serializable {
	Float energy_ev;
	Float detector_resolution_angstroms;
	Float[] beam_size_width_height_microns;
	Float beamstop_position_mm;
	Float phi_delta_degrees;
	Float phi_range_degrees;
	Float exposure_photons_per_image;
	
	public void validate () {
		['beam_size_width_height_microns',
			'beamstop_position_mm',
			'phi_delta_degrees',
			'phi_range_degrees',
			'exposure_photons_per_image',
			'detector_resolution_angstroms'].each { it->
			if ( "this.$it" == null ) {
				throw new Exception("$it must be defined in collection_parameters");
			}
		}
	}

	
}
