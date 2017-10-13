package com.cpm.delegates;

public class ClosingStockBean {
	
	protected String BRAND, SKU, BRAND_CD, SKU_CD, stock, store, opening_stock, mid_stock, listed;
	
	public String getListed() {
		return listed;
	}

	public void setListed(String listed) {
		this.listed = listed;
	}

	public String getOpening_stock() {
		return opening_stock;
	}

	public void setOpening_stock(String opening_stock) {
		this.opening_stock = opening_stock;
	}

	public String getMid_stock() {
		return mid_stock;
	}

	public void setMid_stock(String mid_stock) {
		this.mid_stock = mid_stock;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getCommonId() {
		return commonId;
	}

	public void setCommonId(int commonId) {
		this.commonId = commonId;
	}

	protected int mid, commonId;

	public String getBRAND() {
		return BRAND;
	}

	public void setBRAND(String bRAND) {
		this.BRAND = bRAND;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		this.SKU = sKU;
	}

	public String getBRAND_CD() {
		return BRAND_CD;
	}

	public void setBRAND_CD(String bRAND_CD) {
		this.BRAND_CD = bRAND_CD;
	}

	public String getSKU_CD() {
		return SKU_CD;
	}

	public void setSKU_CD(String sKU_CD) {
		this.SKU_CD = sKU_CD;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	

}
