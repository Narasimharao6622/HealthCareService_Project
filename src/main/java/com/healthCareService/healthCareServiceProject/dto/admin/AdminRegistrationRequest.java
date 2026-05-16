package com.healthCareService.healthCareServiceProject.dto.admin;

import java.time.LocalDate;

public class AdminRegistrationRequest {
	
	
	private String name;
	private String gender;
	private LocalDate dateofbirth;
	private LocalDate joinDate;
	private String email;
	private long mobilenumber;
	private String password;
	private String imageURLPath;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public LocalDate getDateofbirth() {
		return dateofbirth;
	}
	public void setDateofbirth(LocalDate dateofbirth) {
		this.dateofbirth = dateofbirth;
	}
	public LocalDate getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImageURLPath() {
		return imageURLPath;
	}
	public void setImageURLPath(String imageURLPath) {
		this.imageURLPath = imageURLPath;
	}
	
}
