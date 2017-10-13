package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class ConsolidatePerGetterSetter {
	

	
//	"[{\"TAM\":\"65000.00\",\"SALE\":\"5400.00\",\"SHARE\":\"8\",\"TERRITORY\":\"Red\"}]"
	
	
	ArrayList<String> TAM = new ArrayList<String>();
	ArrayList<String> SALE = new ArrayList<String>();
	ArrayList<String> SHARE = new ArrayList<String>();
	ArrayList<String> TERRITORY = new ArrayList<String>();
	
	
	
	public ArrayList<String> getTAM() {
		return TAM;
	}
	public void setTAM(String tAM) {
		this.TAM.add( tAM);
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
		this.SHARE.add( sHARE);
	}
	public ArrayList<String> getTERRITORY() {
		return TERRITORY;
	}
	public void setTERRITORY(String tERRITORY) {
		this.TERRITORY.add(tERRITORY);
	}
	

}
