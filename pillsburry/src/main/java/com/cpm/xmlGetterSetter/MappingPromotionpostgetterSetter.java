package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class MappingPromotionpostgetterSetter {
	

	ArrayList<String> STORE_CD = new ArrayList<String>();
	ArrayList<String> SKU_CD = new ArrayList<String>();

	ArrayList<String> PROMOTION = new ArrayList<String>();
	ArrayList<String> CATEGORY_SEQUENCE = new ArrayList<String>();
	ArrayList<String> BRAND_SEQUENCE = new ArrayList<String>();
	ArrayList<String> BRAND = new ArrayList<String>();
	ArrayList<String> BRAND_CD = new ArrayList<String>();
	ArrayList<String> SKU = new ArrayList<String>();
	ArrayList<String> FOCUS = new ArrayList<String>();
	ArrayList<String> description = new ArrayList<String>();
	ArrayList<String> ID = new ArrayList<String>();

	public  String Remark;
	public  String Promotion_avail;
	public  String image ;

	public int getCommonId() {
		return commonId;
	}

	public void setCommonId(int commonId) {
		this.commonId = commonId;
	}

	public int commonId;

	public String getReasonid() {
		return Reasonid;
	}

	public void setReasonid(String reasonid) {
		Reasonid = reasonid;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		Reason = reason;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPromotion_avail() {
		return Promotion_avail;
	}

	public void setPromotion_avail(String promotion_avail) {
		Promotion_avail = promotion_avail;
	}

	public  String getRemark() {
		return Remark;
	}

	public  void setRemark(String remark) {
		Remark = remark;
	}

	public  String Reasonid="0";
	public  String Reason ;


	public ArrayList<String> getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID.add(ID);


	}

	public ArrayList<String> getSKU_SEQUENCE() {
		return SKU_SEQUENCE;
	}

	public void setSKU_SEQUENCE(String SKU_SEQUENCE) {
		this.SKU_SEQUENCE.add(SKU_SEQUENCE);
	}

	ArrayList<String> SKU_SEQUENCE = new ArrayList<String>();



	
	public ArrayList<String> getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description.add(description);
	}
	
	public ArrayList<String> getFOCUS() {
		return FOCUS;
	}
	public void setFOCUS(String fOCUS) {
		this.FOCUS.add(fOCUS);
	}
	public ArrayList<String> getBRAND() {
		return BRAND;
	}
	public void setBRAND(String bRAND) {
		this.BRAND.add(bRAND);
	}
	public ArrayList<String> getBRAND_CD() {
		return BRAND_CD;
	}
	public void setBRAND_CD(String bRAND_CD) {
		this.BRAND_CD.add(bRAND_CD);
	}
	
	public ArrayList<String> getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		this.SKU.add(sKU);
	}
	
	
	public ArrayList<String> getSTORE_CD() {
		return STORE_CD;
	}
	public void setSTORE_CD(String sTORE_CD) {
		this.STORE_CD.add(sTORE_CD);
	}
	public ArrayList<String> getSKU_CD() {
		return SKU_CD;
	}
	public void setSKU_CD(String sKU_CD) {
		this.SKU_CD.add(sKU_CD);
	}
	public ArrayList<String> getPROMOTION() {
		return PROMOTION;
	}
	public void setPROMOTION(String pROMOTION) {
		this.PROMOTION.add(pROMOTION);
	}
	public ArrayList<String> getCATEGORY_SEQUENCE() {
		return CATEGORY_SEQUENCE;
	}
	public void setCATEGORY_SEQUENCE(String cATEGORY_SEQUENCE) {
		this.CATEGORY_SEQUENCE.add(cATEGORY_SEQUENCE);
	}
	public ArrayList<String> getBRAND_SEQUENCE() {
		return BRAND_SEQUENCE;
	}
	public void setBRAND_SEQUENCE(String bRAND_SEQUENCE) {
		this.BRAND_SEQUENCE.add(bRAND_SEQUENCE);
	}
	
	
	

}
