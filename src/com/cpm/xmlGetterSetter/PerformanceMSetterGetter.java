package com.cpm.xmlGetterSetter;

import java.util.ArrayList;

public class PerformanceMSetterGetter {
	ArrayList<String> monthly_target = new ArrayList<String>();
	ArrayList<String> daily_target = new ArrayList<String>();
	ArrayList<String> achieved = new ArrayList<String>();
	ArrayList<String> pending = new ArrayList<String>();
	
	
	public ArrayList<String> getMonthly_target() {
		return monthly_target;
	}
	public void setMonthly_target(String monthly_target) {
		this.monthly_target.add(monthly_target);
	}
	public ArrayList<String> getDaily_target() {
		return daily_target;
	}
	public void setDaily_target(String daily_target) {
		this.daily_target.add(daily_target);
	}
	public ArrayList<String> getAchieved() {
		return achieved;
	}
	public void setAchieved(String achieved) {
		this.achieved.add(achieved);
	}
	public ArrayList<String> getPending() {
		return pending;
	}
	public void setPending(String pending) {
		this.pending.add(pending);
	}
	
	

}
