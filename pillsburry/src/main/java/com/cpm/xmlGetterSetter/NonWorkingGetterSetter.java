package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class NonWorkingGetterSetter {

	ArrayList<String> reason_id = new ArrayList<String>();
	ArrayList<String> reason = new ArrayList<String>();
	ArrayList<String> entry = new ArrayList<String>();
	ArrayList<String> image = new ArrayList<String>();
	ArrayList<String> reason_remark = new ArrayList<String>();
	public ArrayList<String> getReason_remark() {
		return reason_remark;
	}
	public void setReason_remark(String reason_remark) {
		this.reason_remark.add(reason_remark);
	}
	public ArrayList<String> getReason_id() {
		return reason_id;
	}
	public void setReason_id(String reason_id) {
		this.reason_id.add(reason_id);
	}
	public ArrayList<String> getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason.add(reason);
	}
	public ArrayList<String> getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry.add(entry);
	}
	public ArrayList<String> getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image.add(image);
	}
	
	
	
	
	
	
	

	
}
