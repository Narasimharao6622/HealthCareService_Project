package com.healthCareService.healthCareServiceProject.dto;

import com.healthCareService.healthCareServiceProject.entity.Patient;

public class HomePagePatientResponse {
	private String name;
	private int age;
	private String gender;
	private String emailid;
	private String secondemailid;
	private long mobilenumber;
	private long secondmobilenumber;
	private String bloodgroup;
	private String imagefilepath;
	
	public HomePagePatientResponse(Patient patient) {
		this.name = patient.getName();
		this.age = patient.getAge();
		this.gender = patient.getGender();
		this.emailid = patient.getEmailid();
		this.secondemailid = patient.getSecondemailid();
		this.mobilenumber = patient.getMobilenumber();
		this.secondmobilenumber = patient.getSecondmobilenumber();
		this.bloodgroup = patient.getBloodgroup();
		this.imagefilepath = patient.getImagefilepath();
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

	public String getImagefilepath() {
		return imagefilepath;
	}

	public void setImagefilepath(String imagefilepath) {
		this.imagefilepath = imagefilepath;
	}
	
	
}
