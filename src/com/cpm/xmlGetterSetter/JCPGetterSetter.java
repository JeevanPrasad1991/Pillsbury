package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class JCPGetterSetter {

	ArrayList<String> storeid = new ArrayList<String>();
	ArrayList<String> storename = new ArrayList<String>();
	ArrayList<String> storeaddres = new ArrayList<String>();

	ArrayList<String> coverage_type = new ArrayList<String>();
	ArrayList<String> visitdate = new ArrayList<String>();
	ArrayList<String> storelatitude = new ArrayList<String>();
	ArrayList<String> storelongitude = new ArrayList<String>();
	ArrayList<String> status = new ArrayList<String>();

	ArrayList<String> CATEGORY_ID = new ArrayList<String>();
	ArrayList<String> KEY_ID = new ArrayList<String>();
	ArrayList<String> checkout_status = new ArrayList<String>();
	
	ArrayList<String> CATEGORY = new ArrayList<String>();
	ArrayList<String> TAM = new ArrayList<String>();
	ArrayList<String> RIGHTS = new ArrayList<String>();
	
	
	
	
	
	
	
	public ArrayList<String> getRIGHTS() {
		return RIGHTS;
	}

	public void setRIGHTS(String rIGHTS) {
		this.RIGHTS.add(rIGHTS);
	}

	public ArrayList<String> getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		this.CATEGORY.add(cATEGORY);
	}

	public ArrayList<String> getTAM() {
		return TAM;
	}

	public void setTAM(String tAM) {
		this.TAM.add(tAM);
	}

	

	public ArrayList<String> getCheckout_status() {
		return checkout_status;
	}

	public void setCheckout_status(String checkout_status) {
		this.checkout_status.add(checkout_status);
	}

	public ArrayList<String> getKEY_ID() {
		return KEY_ID;
	}

	public void setKEY_ID(String kEY_ID) {
		this.KEY_ID.add(kEY_ID);
	}

	public ArrayList<String> getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid.add(storeid);
	}

	public ArrayList<String> getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename.add(storename);
	}

	public ArrayList<String> getStoreaddres() {
		return storeaddres;
	}

	public void setStoreaddres(String storeaddres) {
		this.storeaddres.add(storeaddres);
	}

	public ArrayList<String> getCoverage_type() {
		return coverage_type;
	}

	public void setCoverage_type(String coverage_type) {
		this.coverage_type.add(coverage_type);
	}

	public ArrayList<String> getVisitdate() {
		return visitdate;
	}

	public void setVisitdate(String visitdate) {
		this.visitdate.add(visitdate);
	}

	public ArrayList<String> getStorelatitude() {
		return storelatitude;
	}

	public void setStorelatitude(String storelatitude) {
		this.storelatitude.add(storelatitude);
	}

	public ArrayList<String> getStorelongitude() {
		return storelongitude;
	}

	public void setStorelongitude(String storelongitude) {
		this.storelongitude.add(storelongitude);
	}

	public ArrayList<String> getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status.add(status);
	}

	public ArrayList<String> getCATEGORY_ID() {
		return CATEGORY_ID;
	}

	public void setCATEGORY_ID(String cATEGORY_ID) {
		CATEGORY_ID.add(cATEGORY_ID);
	}
	
	
	//**
	
	
	
	

}
