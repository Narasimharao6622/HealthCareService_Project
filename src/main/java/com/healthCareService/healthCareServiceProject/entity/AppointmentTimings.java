package com.healthCareService.healthCareServiceProject.entity;

public enum AppointmentTimings {
	SLOT_10_00("10:00","10:30"),
	SLOT_11_00("11:00","11:30"),
	SLOT_12_00("12:00","12:30"),
	SLOT_02_00("2:00","2:30"),
	SLOT_03_00("3:00","3:30");
	
	
	
	private String startTime;
	private String endTime;
	AppointmentTimings(String startTime, String endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public String getLable() {
		return startTime+":"+endTime;

	}
	
}
