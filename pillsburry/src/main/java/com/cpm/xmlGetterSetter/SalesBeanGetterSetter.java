package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class SalesBeanGetterSetter {
	
	
	
	
	ArrayList<String> brand = new ArrayList<String>();
	ArrayList<String> brandId = new ArrayList<String>();
	ArrayList<String> product_cd = new ArrayList<String>();
	ArrayList<String> product = new ArrayList<String>();

	ArrayList<String> type = new ArrayList<String>();
	ArrayList<String> iscompetitor = new ArrayList<String>();
	
	
	public ArrayList<String> getBrandId() {
		return brandId;
	}
	
	public void setBrandId(String brandId) {
		this.brandId.add(brandId);
	}
	
	
	
	
	public ArrayList<String> getBrand() {
		return brand;
	}
	
	public void setBrand(String brand) {
		this.brand.add(brand);
	}
	
	
	public ArrayList<String> getProduct_cd() {
		return product_cd;
	}
	
	
	public void setProduct_cd(String product_cd) {
		this.product_cd.add(product_cd);
	}
	
	
	public ArrayList<String> getProduct() {
		return product;
	}
	
	public void setProduct(String product) {
		this.product.add(product);
	}
	
	
	public ArrayList<String> getType() {
		return type;
	}
	
	
	public void setType(String type) {
		this.type.add(type);
	}
	
	
	public ArrayList<String> getIscompetitor() {
		return iscompetitor;
	}
	
	public void setIscompetitor(String iscompetitor) {
		this.iscompetitor.add(iscompetitor);
	}
	


}
