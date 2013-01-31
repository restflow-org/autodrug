package org.restflow.addons.crystallography.calculator;

class CrystallographyCalculator {
	
	static public double calcDistanceFromResolution ( resolution, energy, ringSize ) {
		
		if ( resolution <= 0 ) {
			throw new Exception( "0_resolution");
		}
		if ( energy <= 0 ) {
			throw new Exception( "0_energy");
		}
		
		double wavelength = 12398.0 / energy;
		double theta  = Math.asin( (wavelength / (2.0 * resolution)) ) ;
		return ringSize / Math.tan( 2.0 * theta)
	}
	
	
	
}

