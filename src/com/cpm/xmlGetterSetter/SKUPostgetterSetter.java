package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class SKUPostgetterSetter {
	
	
	ArrayList<String> SKU_CD = new ArrayList<String>();
	
	ArrayList<String> SKU = new ArrayList<String>();
	ArrayList<String> BRAND_CD = new ArrayList<String>();
	ArrayList<String> BRAND = new ArrayList<String>();
	ArrayList<String> CATEGORY_CD = new ArrayList<String>();
	
	
	ArrayList<String> AREASON_ID = new ArrayList<String>();
	ArrayList<String> PREASON_ID = new ArrayList<String>();
	ArrayList<String> PREASON = new ArrayList<String>();
	
	
	public ArrayList<String> getPREASON_ID() {
		return PREASON_ID;
	}
	public void setPREASON_ID(String pREASON_ID) {
		this.PREASON_ID.add(pREASON_ID);
	}
	public ArrayList<String> getPREASON() {
		return PREASON;
	}
	public void setPREASON(String pREASON) {
		this.PREASON.add(pREASON);
	}
	
	
	public ArrayList<String> getAREASON_ID() {
		return AREASON_ID;
	}
	public void setAREASON_ID(String aREASON_ID) {
		this.AREASON_ID.add(aREASON_ID);
	}
	public ArrayList<String> getAREASON() {
		return AREASON;
	}
	public void setAREASON(String aREASON) {
		this.AREASON.add(aREASON);
	}
	ArrayList<String> AREASON = new ArrayList<String>();
	
	public ArrayList<String> getCATEGORY() {
		return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY) {
		this.CATEGORY.add(cATEGORY);
	}
	ArrayList<String> CATEGORY = new ArrayList<String>();
	
	
	ArrayList<String> ISCOMPET = new ArrayList<String>();
	
	public ArrayList<String> getISCOMPET() {
		return ISCOMPET;
	}
	public void setISCOMPET(String iSCOMPET) {
		ISCOMPET.add(iSCOMPET);
	}
	public ArrayList<String> getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		this.BRAND.add(bRAND);
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
	public ArrayList<String> getBRAND_CD() {
		return BRAND_CD;
	}
	public void setBRAND_CD(String bRAND_CD) {
		this.BRAND_CD.add(bRAND_CD);
	}
	public ArrayList<String> getCATEGORY_CD() {
		return CATEGORY_CD;
	}
	public void setCATEGORY_CD(String cATEGORY_CD) {
		this.CATEGORY_CD.add(cATEGORY_CD);
	}

}
