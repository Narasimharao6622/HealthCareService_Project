package com.healthCareService.healthCareServiceProject.dto;

import java.util.List;

import com.healthCareService.healthCareServiceProject.entity.Doctor;

public class PatientDTO {
	
	private String name;
	private int age;
	private String gender;
	private String emailid;
	private String secondemailid;
	private long mobilenumber;
	private long secondmobilenumber;
	private String bloodgroup;
	private String password;
	private AddressDTO address;
	
	
	
	public AddressDTO getAddress() {
		return address;
	}
	public void setAddress(AddressDTO address) {
		this.address = address;
	}
	private List<Doctor> consuntantedDoctors;
	
	public List<Doctor> getConsuntantedDoctors() {
		return consuntantedDoctors;
	}
	public void setConsuntantedDoctors(List<Doctor> consuntantedDoctors) {
		this.consuntantedDoctors = consuntantedDoctors;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getSecondemailid() {
		return secondemailid;
	}
	public void setSecondemailid(String secondemailid) {
		this.secondemailid = secondemailid;
	}
	public long getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public long getSecondmobilenumber() {
		return secondmobilenumber;
	}
	public void setSecondmobilenumber(long secondmobilenumber) {
		this.secondmobilenumber = secondmobilenumber;
	}
	public String getBloodgroup() {
		return bloodgroup;
	}
	public void setBloodgroup(String bloodgroup) {
		this.bloodgroup = bloodgroup;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "PatientDTO [name=" + name + ", age=" + age + ", gender=" + gender + ", emailid=" + emailid
				+ ", secondemailid=" + secondemailid + ", mobilenumber=" + mobilenumber + ", secondmobilenumber="
				+ secondmobilenumber + ", bloodgroup=" + bloodgroup + ", password=" + password + ", address=" + address
				+ ", consuntantedDoctors=" + consuntantedDoctors + "]";
	}
	

	
	
}


















