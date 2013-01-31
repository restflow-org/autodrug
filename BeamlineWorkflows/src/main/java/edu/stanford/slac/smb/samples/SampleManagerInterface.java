package edu.stanford.slac.smb.samples;

import java.util.Map;
import sil.beans.Sil;

public interface SampleManagerInterface {

	public Object updateCrystal(String spreadsheetId, String uniqueId,
			Map<String,String> properties);
	
	Sil readSil( String silId );

	public String beamlinePositionAssignedToSil (String beamlineId, String silId);
	
}
