package com.healthCareService.healthCareServiceProject.dto;

public class LoginRequest {
	private String emailid;
	private String password;
	private boolean rememberme;
	
	
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRememberme() {
		return rememberme;
	}
	public void setRemembereme(boolean rememberme) {
		this.rememberme = rememberme;
	}
	@Override
	public String toString() {
		return "LoginRequest [emailid=" + emailid + ", password=" + password + ", rememberme=" + rememberme + "]";
	}
	
	
}
