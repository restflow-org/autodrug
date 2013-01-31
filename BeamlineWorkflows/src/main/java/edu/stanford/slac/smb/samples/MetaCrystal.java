package edu.stanford.slac.smb.samples;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import sil.beans.Crystal;
import sil.beans.CrystalData;
import sil.beans.CrystalResult;
import sil.beans.Image;
import sil.beans.Sil;
//import ssrl.autodrug.parameters.BeamlineDependentValues;
import org.restflow.addons.samples.project.*;

public class MetaCrystal {

	Crystal crystal;
	Sil sil;
	String dewarPort;
	ProteinSpecification strategy;
	//GroupStrategy strategy;
//	BeamlineDependentValues beamlineDependent = new BeamlineDependentValues();
	
	
	public Sil getSil() {
		return sil;
	}
	public void setSil(Sil sil) {
		this.sil = sil;
	}
	public Crystal getCrystal() {
		return crystal;
	}
	public void setCrystal(Crystal crystal) {
		this.crystal = crystal;
	}
	
	public String getBarcode() {
		return crystal.getBarcode();
	}
	public String getBarcodeScanned() {
		return crystal.getBarcodeScanned();
	}
	public String getContainerId() {
		return crystal.getContainerId();
	}
	public String getContainerType() {
		return crystal.getContainerType();
	}
	public String getCrystalId() {
		return crystal.getCrystalId();
	}
	public CrystalData getData() {
		return crystal.getData();
	}
	public int getEventId() {
		return crystal.getEventId();
	}
	public int getExcelRow() {
		return crystal.getExcelRow();
	}
	public Map<String, Image> getImages() {
		return crystal.getImages();
	}
	public String getName() {
		return crystal.getName();
	}
	public String getPort() {
		return crystal.getPort();
	}
	public CrystalResult getResult() {
		return crystal.getResult();
	}
	public int getRow() {
		return crystal.getRow();
	}
	public boolean getSelected() {
		return crystal.getSelected();
	}
	public long getUniqueId() {
		return crystal.getUniqueId();
	}
	public boolean isSelectedForQueue() {
		return crystal.isSelectedForQueue();
	}
	public void setBarcode(String barcode) {
		crystal.setBarcode(barcode);
	}
	public void setBarcodeScanned(String barcodeScanned) {
		crystal.setBarcodeScanned(barcodeScanned);
	}
	public void setContainerId(String containerId) {
		crystal.setContainerId(containerId);
	}
	public void setContainerType(String containerType) {
		crystal.setContainerType(containerType);
	}
	public void setCrystalId(String crystalId) {
		crystal.setCrystalId(crystalId);
	}
	public void setData(CrystalData data) {
		crystal.setData(data);
	}
	public void setEventId(int eventId) {
		crystal.setEventId(eventId);
	}
	public void setExcelRow(int r) {
		crystal.setExcelRow(r);
	}
	public void setImages(Map<String, Image> images) {
		crystal.setImages(images);
	}
	public void setName(String name) {
		crystal.setName(name);
	}
	public void setPort(String port) {
		crystal.setPort(port);
	}
	public void setResult(CrystalResult result) {
		crystal.setResult(result);
	}
	public void setRow(int r) {
		crystal.setRow(r);
	}
	public void setSelected(boolean l) {
		crystal.setSelected(l);
	}
	public void setSelected(String n) {
		crystal.setSelected(n);
	}
	public void setSelectedForQueue(boolean selectedForQueue) {
		crystal.setSelectedForQueue(selectedForQueue);
	}
	public void setUniqueId(long uniqueId) {
		crystal.setUniqueId(uniqueId);
	}
	
	
	
	public String getDewarPort() {
		return dewarPort;
	}
	public void setDewarPort(String dewarPort) {
		this.dewarPort = dewarPort;
	}
	public ProteinSpecification getStrategy() {
		return strategy;
	}
	public void setStrategy(ProteinSpecification  strategy) {
		this.strategy = strategy;
	}
	@Override
	public String toString() {
		
		try {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("port", getPort());
			map.put("name", getName());
			map.put("protein", getData().getProtein());
			map.put("spreadsheetId", getSil().getId() );		
		
			DumperOptions options = new DumperOptions();
			options.setWidth(200);
			options.setIndent(2);
		
			Yaml yaml = new Yaml(options);
			return yaml.dump(map).trim();
		} catch (Exception e) {
			return super.toString();
		}
	}
	
	
	
	
}
