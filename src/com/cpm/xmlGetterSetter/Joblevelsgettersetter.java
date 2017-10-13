package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class Joblevelsgettersetter {
	
	ArrayList<String> joblevel_cd = new ArrayList<String>();
	ArrayList<String> joblevel = new ArrayList<String>();
	
	public ArrayList<String> getjoblevel_cd() {
		return joblevel_cd;
	}
	public void setjoblevel_cd(String joblevel_cd) {
		this.joblevel_cd.add(joblevel_cd);
	}
	public ArrayList<String> getJoblevel() {
		return joblevel;
	}
	public void setjoblevel(String joblevel) {
		this.joblevel.add(joblevel);
	}
	

}
