package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class MappingAssetPostgetterSetter {
	
	ArrayList<String> STORE_CD = new ArrayList<String>();
	ArrayList<String> SKU_CD = new ArrayList<String>();
	ArrayList<String> SKU = new ArrayList<String>();
	ArrayList<String> ASSET_CD = new ArrayList<String>();
	ArrayList<String> ASSET = new ArrayList<String>();
	ArrayList<String> BRAND = new ArrayList<String>();
	ArrayList<String> count = new ArrayList<String>();
	
	
	public ArrayList<String> getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count.add(count);
	}
	public ArrayList<String> getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		this.BRAND.add(bRAND);
	}
	public ArrayList<String> getBRAND_CD() {
		return BRAND_CD;
	}
	public void setBRAND_CD(String bRAND_CD) {
		this.BRAND_CD.add(bRAND_CD);
	}
	ArrayList<String> BRAND_CD = new ArrayList<String>();
	
	public ArrayList<String> getSTORE_CD() {
		return STORE_CD;
	}
	public void setSTORE_CD(String sTORE_CD) {
		this.STORE_CD.add(sTORE_CD);
	}
	public ArrayList<String> getSKU_CD() {
		return SKU_CD;
	}
	public void setSKU_CD(String sKU_CD) {
		this.SKU_CD.add(sKU_CD);
	}
	public ArrayList<String> getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		this.SKU.add(sKU);
	}
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
