package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class AssetPostgetterSetter {
	
	ArrayList<String> ASSET_CD = new ArrayList<String>();
	ArrayList<String> ASSET = new ArrayList<String>();
	
	
	ArrayList<String> POSM_CD = new ArrayList<String>();
	public ArrayList<String> getPOSM_CD() {
		return POSM_CD;
	}
	public void setPOSM_CD(String pOSM_CD) {
		this.POSM_CD.add(pOSM_CD);
	}
	public ArrayList<String> getPOSM() {
		return POSM;
	}
	public void setPOSM(String pOSM) {
		this.POSM.add(pOSM);
	}
	ArrayList<String> POSM = new ArrayList<String>();
	
	
	
	
	
	public ArrayList<String> getASSET_CD() {
		return ASSET_CD;
	}
	public void setASSET_CD(String aSSET_CD) {
		this.ASSET_CD.add(aSSET_CD);
	}
	public ArrayList<String> getASSET() {
		return ASSET;
	}
	public void setASSET(String aSSET) {
		this.ASSET.add(aSSET);
	}
	
	
	

}
