package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class MappingPromotionpostgetterSetter {
	
	
	ArrayList<String> STORE_CD = new ArrayList<String>();
	ArrayList<String> SKU_CD = new ArrayList<String>();

	ArrayList<String> PROMOTION = new ArrayList<String>();
	ArrayList<String> CATEGORY_SEQUENCE = new ArrayList<String>();
	ArrayList<String> BRAND_SEQUENCE = new ArrayList<String>();
	ArrayList<String> BRAND = new ArrayList<String>();
	ArrayList<String> BRAND_CD = new ArrayList<String>();
	ArrayList<String> SKU = new ArrayList<String>();
	ArrayList<String> FOCUS = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();
	
	
	public ArrayList<String> getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description.add(description);
	}
	
	public ArrayList<String> getFOCUS() {
		return FOCUS;
	}
	public void setFOCUS(String fOCUS) {
		this.FOCUS.add(fOCUS);
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
	
	public ArrayList<String> getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		this.SKU.add(sKU);
	}
	
	
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
	public ArrayList<String> getPROMOTION() {
		return PROMOTION;
	}
	public void setPROMOTION(String pROMOTION) {
		this.PROMOTION.add(pROMOTION);
	}
	public ArrayList<String> getCATEGORY_SEQUENCE() {
		return CATEGORY_SEQUENCE;
	}
	public void setCATEGORY_SEQUENCE(String cATEGORY_SEQUENCE) {
		this.CATEGORY_SEQUENCE.add(cATEGORY_SEQUENCE);
	}
	public ArrayList<String> getBRAND_SEQUENCE() {
		return BRAND_SEQUENCE;
	}
	public void setBRAND_SEQUENCE(String bRAND_SEQUENCE) {
		this.BRAND_SEQUENCE.add(bRAND_SEQUENCE);
	}
	
	
	

}
