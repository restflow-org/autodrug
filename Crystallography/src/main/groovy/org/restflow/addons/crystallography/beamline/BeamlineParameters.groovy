package org.restflow.addons.crystallography.beamline;

class BeamlineParameters {
	String beamline;
	def detectorRingSize;
	def phiSpeedDegPerSec;
	Motor energy;
	Motor detector_z;
	def flux_photons_per_second;
	def detectorGain_countsPerPhoton;
	def minimumExposure_seconds;
	def abortCount;
	def activeKey;
}

class Motor {
	def name;
	def upperLimit
	def lowerLimit

	public void assertLimits (def position) {
		assertLowerLimit(position);
		assertUpperLimit(position);
	}
		
	public void assertLowerLimit (def position) {
		if ( position < lowerLimit ) throw new Exception ("position $position would exceed $name lower limit $lowerLimit");
	}

	public void assertUpperLimit (def position) {
		if ( position > upperLimit ) throw new Exception ("position $position would exceed $name upper limit $upperLimit");
	}
}
