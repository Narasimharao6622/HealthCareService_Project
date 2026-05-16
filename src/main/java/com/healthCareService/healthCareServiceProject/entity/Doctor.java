package com.healthCareService.healthCareServiceProject.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doctorid;
	private String name;
	private String specialization;
	private int age;
	private String gender;
	private long number;
	private String email;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	private int salary;
	private int noofcasesaccepted;
	private int noofcaseshold;
	private int totalrating;

	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "date_id",nullable = false)
	private List<WorkingDates> workingdates;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Rating> rating;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "consaltentdoctors")
	private List<Patient> consaltentpatients;

	private String imagefilepath;
	// Getters And Setters

	public List<Patient> getConsaltentpatients() {
		return consaltentpatients;
	}

	public String getImagefilepath() {
		return imagefilepath;
	}

	public void setImagefilepath(String imagefilepath) {
		this.imagefilepath = imagefilepath;
	}

	public int getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(int doctorid) {
		this.doctorid = doctorid;
	}

	public int getTotalrating() {
		return totalrating;
	}

	public void setTotalrating(int totalrating) {
		this.totalrating = totalrating;
	}

	public List<Rating> getRating() {
		return rating;
	}

	public void setRating(List<Rating> rating) {
		this.rating = rating;
	}

	public void setConsaltentpatients(List<Patient> consaltentpatients) {
		this.consaltentpatients = consaltentpatients;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
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

	
	public List<WorkingDates> getWorkingdates() {
		return workingdates;
	}

	public void setWorkingdates(List<WorkingDates> workingdates) {
		this.workingdates = workingdates;
	}

//	@Override
//	public String toString() {
//		return "Doctor [doctorid=" + doctorid + ", name=" + name + ", specialization=" + specialization + ", age=" + age
//				+ ", gender=" + gender + ", number=" + number + ", email=" + email + ", address=" + address
//				+ ", salary=" + salary + ", noofcasesaccepted=" + noofcasesaccepted + ", noofcaseshold=" + noofcaseshold
//				+ ", totalrating=" + totalrating + ", workingdates=" + workingdates + ", rating=" + rating
//				+ ", consaltentpatients=" + consaltentpatients + ", imagefilepath=" + imagefilepath + "]";
//	}


}
