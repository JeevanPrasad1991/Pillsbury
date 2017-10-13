package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class storekeycontactdetailsgettersetter {
	
	ArrayList<String> contact_cd = new ArrayList<String>();
	ArrayList<String> store_cd = new ArrayList<String>();
	ArrayList<String> title = new ArrayList<String>();
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> designation_cd = new ArrayList<String>();
	ArrayList<String> joblevel_id= new ArrayList<String>();
	ArrayList<String> business_email = new ArrayList<String>();
	ArrayList<String>  mobile= new ArrayList<String>();
	ArrayList<String> business_phone = new ArrayList<String>();
	
	
	
	public ArrayList<String> getContact_cd() {
		return contact_cd;
	}
	public void setContact_cd(String contact_cd) {
		this.contact_cd.add(contact_cd);
	}
	public ArrayList<String> getStore_cd() {
		return store_cd;
	}
	public void setStore_cd(String store_cd) {
		this.store_cd.add(store_cd);
	}
	public ArrayList<String> getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title.add(title);
	}
	public ArrayList<String> getName() {
		return name;
	}
	public void setName(String name) {
		this.name.add(name);
	}
	public ArrayList<String> getDesignation_cd() {
		return designation_cd;
	}
	public void setDesignation_cd(String designation_cd) {
		this.designation_cd.add(designation_cd);
	}
	public ArrayList<String> getJoblevel_id() {
		return joblevel_id;
	}
	public void setJoblevel_id(String joblevel) {
		this.joblevel_id.add(joblevel);
		}
	public ArrayList<String> getBusiness_email() {
		return business_email;
	}
	public void setBusiness_email(String business_email) {
		this.business_email.add(business_email);
	}
	public ArrayList<String> getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile.add(mobile);
	}
	public ArrayList<String> getBusiness_phone() {
		return business_phone;
	}
	public void setBusiness_phone(String business_phone) {
		this.business_phone.add(business_phone);
	}
	

}
