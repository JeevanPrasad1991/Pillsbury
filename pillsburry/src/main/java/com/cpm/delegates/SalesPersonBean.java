package com.cpm.delegates;

public class SalesPersonBean {
	public String contact_cd;
	public String name,flag,desc_id;
	int id = 0;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc_id() {
		return desc_id;
	}

	public void setDesc_id(String desc_id) {
		this.desc_id = desc_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String designation;
	public String title;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJoblevel() {
		return joblevel;
	}

	public void setJoblevel(String joblevel) {
		this.joblevel = joblevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String joblevel;
	public String email;
	public String mobile;
	public String phone;
	
	

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getContact_cd() {
		return contact_cd;
	}

	public void setContact_cd(String des_cd) {
		this.contact_cd = des_cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String des) {
		this.name = des;
	}

}
