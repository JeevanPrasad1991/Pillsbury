package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class AvailabilitygetterSetter {
	
	ArrayList<String> STORE_CD = new ArrayList<String>();
	ArrayList<String> SKU_CD = new ArrayList<String>();
	ArrayList<String> SKU = new ArrayList<String>();
	ArrayList<String> MBQ = new ArrayList<String>();
	ArrayList<String> IDEAL_SHELF = new ArrayList<String>();
	ArrayList<String> CATEGORY_SEQUENCE = new ArrayList<String>();
	ArrayList<String> BRAND_SEQUENCE = new ArrayList<String>();
	ArrayList<String> FACING_ALLOW = new ArrayList<String>();
	ArrayList<String> PRICING_ALLOW = new ArrayList<String>();
	ArrayList<String> STOCK_ALLOW = new ArrayList<String>();
	ArrayList<String> FOCUS = new ArrayList<String>();
	ArrayList<String> SKU_SEQUENCE = new ArrayList<String>();
	
	ArrayList<String> ALLOW_SHELF = new ArrayList<String>();
	public ArrayList<String> getALLOW_SHELF() {
		return ALLOW_SHELF;
	}
	public void setALLOW_SHELF(String aLLOW_SHELF) {
		ALLOW_SHELF.add(aLLOW_SHELF);
	}
	public ArrayList<String> getALLOW_SIGNAGE() {
		return ALLOW_SIGNAGE;
	}
	public void setALLOW_SIGNAGE(String aLLOW_SIGNAGE) {
		ALLOW_SIGNAGE.add(aLLOW_SIGNAGE);
	}
	public ArrayList<String> getALLOW_POG() {
		return ALLOW_POG;
	}
	public void setALLOW_POG(String aLLOW_POG) {
		ALLOW_POG.add(aLLOW_POG);
	}
	public ArrayList<String> getALLOW_EXPRING_STOCK() {
		return ALLOW_EXPRING_STOCK;
	}
	public void setALLOW_EXPRING_STOCK(String aLLOW_EXPRING_STOCK) {
		ALLOW_EXPRING_STOCK.add(aLLOW_EXPRING_STOCK);
	}
	ArrayList<String> ALLOW_SIGNAGE = new ArrayList<String>();
	
	ArrayList<String> ALLOW_POG = new ArrayList<String>();
	ArrayList<String> ALLOW_EXPRING_STOCK = new ArrayList<String>();
	
	
	
	
	public ArrayList<String> getSKU_SEQUENCE() {
		return SKU_SEQUENCE;
	}
	public void setSKU_SEQUENCE(String sKU_SEQUENCE) {
		SKU_SEQUENCE.add(sKU_SEQUENCE);
	}
	public ArrayList<String> getFOCUS() {
		return FOCUS;
	}
	public void setFOCUS(String fOCUS) {
		this.FOCUS.add(fOCUS);
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
	public ArrayList<String> getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		this.SKU.add(sKU);
	}
	public ArrayList<String> getMBQ() {
		return MBQ;
	}
	public void setMBQ(String mBQ) {
		this.MBQ.add(mBQ);
	}
	public ArrayList<String> getIDEAL_SHELF() {
		return IDEAL_SHELF;
	}
	public void setIDEAL_SHELF(String iDEAL_SHELF) {
		this.IDEAL_SHELF.add(iDEAL_SHELF);
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
	public ArrayList<String> getFACING_ALLOW() {
		return FACING_ALLOW;
	}
	public void setFACING_ALLOW(String fACING_ALLOW) {
		this.FACING_ALLOW.add(fACING_ALLOW);
	}
	public ArrayList<String> getPRICING_ALLOW() {
		return PRICING_ALLOW;
	}
	public void setPRICING_ALLOW(String pRICING_ALLOW) {
		this.PRICING_ALLOW.add(pRICING_ALLOW);
	}
	public ArrayList<String> getSTOCK_ALLOW() {
		return STOCK_ALLOW;
	}
	public void setSTOCK_ALLOW(String sTOCK_ALLOW) {
		this.STOCK_ALLOW.add(sTOCK_ALLOW);
	}
	

}
