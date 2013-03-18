package org.restflow.addons.samples.screen;

import org.restflow.addons.samples.raster.*;

class ScreenParameters implements Serializable {
	Float detector_resolution_angstroms;
	Float energy_ev
	
	Float[] beam_size_width_height_microns;
	Float beamstop_position_mm;
	
	Float minimum_score;
	
	//Float attenuation
	//Float[] image_angles_degrees;
	Float phi_delta_degrees;
	Float exposure_photons_per_image;
	
	RasterParameters rastering_parameters;
	
	public void validate () {
		['detector_resolution_angstroms',
			'energy_ev',
			'beam_size_width_height_microns',
			'beamstop_position_mm',
//			'image_angles_degrees',
			'phi_delta_degrees',
			'minimum_score',
			'exposure_photons_per_image'].each { field ->
			if ( this."$field" == null ) {
				throw new Exception("$field must be defined in screening_parameters");
			}
		}
		if (rastering_parameters == null) {
			//the rastering block wasn't defined so make a 
			rastering_parameters = new RasterParameters();
			rastering_parameters.active = false;
			rastering_parameters.peak_cutoff_percent = 0;
		}
		rastering_parameters.inheritFromScreeningParameters(this);
		rastering_parameters.validate();
	}
}
