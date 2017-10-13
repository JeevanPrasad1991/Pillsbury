package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class posmgettersetter {
	
	ArrayList<String> posm_cd = new ArrayList<String>();
	ArrayList<String> posm = new ArrayList<String>();
	
	public ArrayList<String> getposm_cd() {
		return posm_cd;
	}
	public void setposm_id(String posm_cd) {
		this.posm_cd.add(posm_cd);
	}
	public ArrayList<String> getposm() {
		return posm;
	}
	public void setposm(String posm) {
		this.posm.add(posm);
	}

}
