package com.cpm.delegates;

public class CoverageBean
{
	protected int MID;
	
	protected String Rights;
	
	public String getRights() {
		return Rights;
	}

	public void setRights(String rights) {
		Rights = rights;
	}

	protected String storeId;
	
	protected String userId;
	protected String Outlook_Remark;
	
	

	public String getOutlook_Remark() {
		return Outlook_Remark;
	}

	public void setOutlook_Remark(String outlook_Remark) {
		Outlook_Remark = outlook_Remark;
	}

	protected String Remark="";
	
	protected String inTime;
	protected String chkoutstatus="";
	
	public String getChkoutstatus() {
		return chkoutstatus;
	}

	public void setChkoutstatus(String chkoutstatus) {
		this.chkoutstatus = chkoutstatus;
	}

	protected String outTime;
	
	protected String visitDate;
	
	protected String keycontactId;
	
	protected String isdDeploy;
	
	protected String uploadStatus;
	
	private String latitude;
	
	private String longitude;
	
	private String reasonid="";
	
	private String reason="";
	
	private String status="N";
	
	private String image="";
	
	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMID() {
		return MID;
	}

	public void setMID(int mID) {
		MID = mID;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	public String getKeycontactId() {
		return keycontactId;
	}

	public void setKeycontactId(String keycontactId) {
		this.keycontactId = keycontactId;
	}

	public String getIsdDeploy() {
		return isdDeploy;
	}

	public void setIsdDeploy(String isdDeploy) {
		this.isdDeploy = isdDeploy;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getReasonid() {
		return reasonid;
	}

	public void setReasonid(String reasonid) {
		this.reasonid = reasonid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
