package com.healthCareService.healthCareServiceProject.dto;

import java.util.List;

import com.healthCareService.healthCareServiceProject.entity.Rating;
import com.healthCareService.healthCareServiceProject.entity.WorkingDates;

public class DoctorDTO {
	private String doctorid;
	private String name;
	private String specialization;
	private int age;
	private String gender;
	private long number;
	private String email;
	private int experiance;

	private AddressDTO address;

	private int salary;
	private int noofcasesaccepted;
	private int noofcaseshold;
	private double totalrating;

	private List<WorkingDates> workingdates;

	private List<Rating> rating;

	private String imagefilepath;

	
	public int getExperiance() {
		return experiance;
	}

	public void setExperiance(int experiance) {
		this.experiance = experiance;
	}

	public String getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
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

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AddressDTO getAddress() {
		return address;
	}

	public void setAddress(AddressDTO address) {
		this.address = address;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getNoofcasesaccepted() {
		return noofcasesaccepted;
	}

	public void setNoofcasesaccepted(int noofcasesaccepted) {
		this.noofcasesaccepted = noofcasesaccepted;
	}

	public int getNoofcaseshold() {
		return noofcaseshold;
	}

	public void setNoofcaseshold(int noofcaseshold) {
		this.noofcaseshold = noofcaseshold;
	}

	public double getTotalrating() {
		return totalrating;
	}

	public void setTotalrating(double totalrating) {
		this.totalrating = totalrating;
	}

	public List<WorkingDates> getWorkingdates() {
		return workingdates;
	}

	public void setWorkingdates(List<WorkingDates> workingdates) {
		this.workingdates = workingdates;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public String getImagefilepath() {
		return imagefilepath;
	}

	public void setImagefilepath(String imagefilepath) {
		this.imagefilepath = imagefilepath;
	}

	@Override
	public String toString() {
		return "Doctor [doctorid=" + doctorid + ", name=" + name + ", specialization=" + specialization + ", age=" + age
				+ ", gender=" + gender + ", number=" + number + ", email=" + email + ", address=" + address
				+ ", salary=" + salary + ", noofcasesaccepted=" + noofcasesaccepted + ", noofcaseshold=" + noofcaseshold
				+ ", totalrating=" + totalrating + ", workingdates=" + workingdates + ", rating=" + rating
				+ " imagefilepath=" + imagefilepath + "]";
	}

//	public List<Rating> getRating() {
//		return rating;
//	}
//	public void setRating(List<Rating> rating) {
//		this.rating = rating;
//	}

}
