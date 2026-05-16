package com.healthCareService.healthCareServiceProject.dto.admin;

import java.time.LocalDate;

import com.healthCareService.healthCareServiceProject.dto.AddressDTO;

public class AdminDTO {	
	private String adminid;
	
	private String name;
	private String gender;
	private LocalDate dateofbirth;
	private LocalDate joinDate;
	private String email;
	private long mobilenumber;
	private String password;
	private String imageURLPath;
	private AddressDTO address;
	private double salary;
	private boolean activeStatus;
	

	public AdminDTO(String name, String gender, LocalDate dateofbirth, LocalDate joinDate, String email,
			long mobilenumber, String password, String imageURLPath) {
		super();
		this.name = name;
		this.gender = gender;
		this.dateofbirth = dateofbirth;
		this.joinDate = joinDate;
		this.email = email;
		this.mobilenumber = mobilenumber;
		this.password = password;
		this.imageURLPath = imageURLPath;
	}

	public AdminDTO(String userid,String password) {
		this.adminid = userid;
		this.password = password;
	}


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

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public String getAdminid() {
		return adminid;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public void setAdminid(String adminid) {
		this.adminid = adminid;
	}
	
	
}
