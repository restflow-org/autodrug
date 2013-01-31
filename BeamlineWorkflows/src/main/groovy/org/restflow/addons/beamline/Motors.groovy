package org.restflow.addons.beamline;

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
