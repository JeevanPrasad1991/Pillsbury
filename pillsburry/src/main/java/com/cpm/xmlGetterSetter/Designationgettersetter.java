package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class Designationgettersetter {
	
	ArrayList<String> designation_cd = new ArrayList<String>();
	ArrayList<String> designation = new ArrayList<String>();
	
	public ArrayList<String> getdesignation_cd() {
		return designation_cd;
	}
	public void setdesignation_cd(String designation_cd) {
		this.designation_cd.add(designation_cd);
	}
	public ArrayList<String> getDesignation() {
		return designation;
	}
	public void setdesignation(String designation) {
		this.designation.add(designation);
	}
	
	
	

}
