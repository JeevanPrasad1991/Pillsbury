package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class planogramSetterGetter {
	
	ArrayList<String> STORE_CD = new ArrayList<String>();
	ArrayList<String> BRAND_CD = new ArrayList<String>();
	ArrayList<String> img_URL = new ArrayList<String>();
	
	
	public ArrayList<String> getSTORE_CD() {
		return STORE_CD;
	}
	public void setSTORE_CD(String sTORE_CD) {
	this.STORE_CD.add(sTORE_CD);
	}
	public ArrayList<String> getBRAND_CD() {
		return BRAND_CD;
	}
	public void setBRAND_CD(String bRAND_CD) {
		this.BRAND_CD.add(bRAND_CD);
	}
	public ArrayList<String> getImg_URL() {
		return img_URL;
	}
	public void setImg_URL(String img_URL) {
		this.img_URL.add(img_URL);
	}
	

}
