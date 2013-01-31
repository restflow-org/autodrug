package org.restflow.addons.samples.collect;

import org.yaml.snakeyaml.*;

//class that closely matches the data needed by the Blu-Ice CollectRun operation.
class CollectRunDefinition {
	Float energy_ev;
	Float beamstop_position_mm;
	Float phi_delta_degrees;

    Float wedge;
    Float startAngle;
    Float endAngle;

    Float exposureTime;
    Float attenuation;
    Float distance;
    Boolean inverse;
    Float beam_size_width_mm;
    Float beam_size_height_mm;

    public String toString() {
          
        Yaml yaml = new Yaml();
        return yaml.dump (this);
    }

}



