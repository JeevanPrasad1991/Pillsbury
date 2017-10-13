package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class TamgetterSetter {
	
	ArrayList<String> store_cd = new ArrayList<String>();
	ArrayList<String> Brand_cd = new ArrayList<String>();
	ArrayList<String> Tam = new ArrayList<String>();
	ArrayList<String> BrandName = new ArrayList<String>();
	
	
	public ArrayList<String> getBrandName() {
		return BrandName;
	}
	public void setBrandName(String brandName) {
		this.BrandName.add(brandName);
	}
	public ArrayList<String> getStore_cd() {
		return store_cd;
	}
	public void setStore_cd(String store_cd) {
		this.store_cd.add(store_cd);
	}
	public ArrayList<String> getBrand_cd() {
		return Brand_cd;
	}
	public void setBrand_cd(String brand_cd) {
		this.Brand_cd.add(brand_cd);
	}
	public ArrayList<String> getTam() {
		return Tam;
	}
	public void setTam(String tam) {
		this.Tam.add(tam);
	}


}
