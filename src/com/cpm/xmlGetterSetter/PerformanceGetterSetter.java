package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class PerformanceGetterSetter {
	
	//"[{\"STORE_CD\":\"3\",\"STORENAME\":\"Lynx Technologies\",\"CITY\":\"Chennai\",\"
	//+ ""TAM\":\"5000.00\",\"SALE\":\"3150.00\",\"SHARE\":\"63\",\"TERRITORY\":\"Blue\"},
	
	
	ArrayList<String> STORENAME = new ArrayList<String>();
	ArrayList<String> CITY = new ArrayList<String>();
	ArrayList<String> TAM = new ArrayList<String>();
	ArrayList<String> SALE = new ArrayList<String>();
	ArrayList<String> SHARE = new ArrayList<String>();
	ArrayList<String> TERRITORY = new ArrayList<String>();
	ArrayList<String> STORE_CD = new ArrayList<String>();
	
	
	
	public ArrayList<String> getSTORE_CD() {
		return STORE_CD;
	}
	public void setSTORE_CD(String sTORE_CD) {
		this.STORE_CD.add(sTORE_CD);
	}
	public ArrayList<String> getSTORENAME() {
		return STORENAME;
	}
	public void setSTORENAME(String sTORENAME) {
		this.STORENAME.add(sTORENAME);
	}
	public ArrayList<String> getCITY() {
		return CITY;
	}
	public void setCITY(String cITY) {
		this.CITY.add(cITY);
	}
	public ArrayList<String> getTAM() {
		return TAM;
	}
	public void setTAM(String tAM) {
		this.TAM.add(tAM);
	}
	public ArrayList<String> getSALE() {
		return SALE;
	}
	public void setSALE(String sALE) {
		this.SALE.add(sALE);
	}
	public ArrayList<String> getSHARE() {
		return SHARE;
	}
	public void setSHARE(String sHARE) {
		this.SHARE.add(sHARE);
	}
	public ArrayList<String> getTERRITORY() {
		return TERRITORY;
	}
	public void setTERRITORY(String tERRITORY) {
		this.TERRITORY.add(tERRITORY);
	}
	

}
