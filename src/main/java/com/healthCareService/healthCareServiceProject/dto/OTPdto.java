package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalTime;

public class OTPdto {
	private String whichDomin;
	private String otp;
	private LocalTime timeStamp;
	private long timeLimit;
	
	
	public String getWhichDomin() {
		return whichDomin;
	}
	public void setWhichDomin(String whichDomin) {
		this.whichDomin = whichDomin;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	@Override
	public String toString() {
		return "OTPdto [email=" + whichDomin + ", otp=" + otp + ", timeStamp=" + timeStamp + ", timeLimit=" + timeLimit
				+ "]";
	}
	
	
}
