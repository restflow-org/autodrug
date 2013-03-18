package org.restflow.addons.samples.raster;
import org.restflow.addons.samples.screen.*;

class RasterParameters implements Serializable {
	boolean active = true;
	
	Float peak_cutoff_percent;
	
	Float detector_resolution_angstroms;
	Float energy_ev
	
	Float beamstop_position_mm;
	
	Float phi_delta_degrees;
	Float exposure_photons_per_image;
	
	
	public void inheritFromScreeningParameters(ScreenParameters screeningParameters) {
		[	'detector_resolution_angstroms',
			'energy_ev',
			'beamstop_position_mm',
			'phi_delta_degrees',
			'exposure_photons_per_image'].each { field ->
			if ( this."$field" == null ) {
				this."$field" = screeningParameters."$field";
			}
		}
	}
	
	public void validate () {
		['peak_cutoff_percent',
			'detector_resolution_angstroms',
			'energy_ev',
			'beamstop_position_mm',
			'phi_delta_degrees',
			'exposure_photons_per_image'].each { field ->
			if ( this."$field" == null ) {
				throw new Exception("$field must be defined in rastering_parameters");
			}
		}
	}
	
	
}
