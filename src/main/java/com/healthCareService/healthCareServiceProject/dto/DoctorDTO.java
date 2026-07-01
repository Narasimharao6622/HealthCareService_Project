package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalDate;
import java.util.List;

import com.healthCareService.healthCareServiceProject.entity.Appointment;
import com.healthCareService.healthCareServiceProject.entity.DoctorAvilability;
import com.healthCareService.healthCareServiceProject.entity.Rating;

public class DoctorDTO {
	private String doctorid;
	private String name;
	private String specialization;
	private int age;
	private LocalDate dateofbirth;
	private String gender;
	private long number;
	private String email;
	private int experiance;
	private String password;

	private AddressDTO address;

	private LocalDate joindate;
	private double salary;
	private int noofcasesaccepted;
	private int noofcaseshold;
	private double totalrating;
	
	private DoctorAvilability avilability;

	private List<Rating> rating;
	private List<Appointment> appointments;

	private String imagefilepath;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public LocalDate getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(LocalDate dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	public LocalDate getJoindate() {
		return joindate;
	}

	public void setJoindate(LocalDate joindate) {
		this.joindate = joindate;
	}

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

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
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
				+ ", totalrating=" + totalrating + ", rating=" + rating
				+ " imagefilepath=" + imagefilepath + "]";
	}

	public void setAvilability(DoctorAvilability avilability) {
		this.avilability = avilability;
	}
	public DoctorAvilability getAvilability() {
		return avilability;
	}

//	public List<Rating> getRating() {
//		return rating;
//	}
//	public void setRating(List<Rating> rating) {
//		this.rating = rating;
//	}

}
