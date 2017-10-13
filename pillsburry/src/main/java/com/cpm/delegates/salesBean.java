package com.cpm.delegates;

public class salesBean {
	
	protected String Brand,Product_cd,Product,Type,Iscompetitor,unitSold,price,sales,BrandID;
	
	public String getBrandID() {
		return BrandID;
	}

	public void setBrandID(String brandID) {
		BrandID = brandID;
	}

	protected String lastmonth_sale,tilldate_sale,sales_Outlook;

	public String getLastmonth_sale() {
		return lastmonth_sale;
	}

	public void setLastmonth_sale(String lastmonth_sale) {
		this.lastmonth_sale = lastmonth_sale;
	}

	public String getTilldate_sale() {
		return tilldate_sale;
	}

	public void setTilldate_sale(String tilldate_sale) {
		this.tilldate_sale = tilldate_sale;
	}

	public String getSales_Outlook() {
		return sales_Outlook;
	}

	public void setSales_Outlook(String sales_Outlook) {
		this.sales_Outlook = sales_Outlook;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}

	public String getUnitSold() {
		return unitSold;
	}

	public void setUnitSold(String unitSold) {
		this.unitSold = unitSold;
	}

	public String getBrand() {
		return Brand;
	}

	public void setBrand(String brand) {
		Brand = brand;
	}

	public String getProduct_cd() {
		return Product_cd;
	}

	public void setProduct_cd(String product_cd) {
		Product_cd = product_cd;
	}

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getIscompetitor() {
		return Iscompetitor;
	}

	public void setIscompetitor(String iscompetitor) {
		Iscompetitor = iscompetitor;
	}

}
