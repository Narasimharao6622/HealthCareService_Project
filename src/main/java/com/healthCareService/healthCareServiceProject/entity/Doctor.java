package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Doctor {
	@Id
	private String doctorid;
	private String name;
	private String specialization;
	private int age;
	private LocalDate dateofbirth;
	private String gender;
	private long number;
	private String email;
	private String password;
	private int experiance;

	@OneToOne(cascade = CascadeType.ALL)
	private Address address;

	private LocalDate joindate;
	private double salary;
	private int noofcasesaccepted;
	private int noofcaseshold;
	private double totalrating;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "ratingid")
	private List<Rating> rating;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "consaltantdoctors")
	private List<Patient> consaltentpatients;
	
	@OneToMany(mappedBy = "doctor")
	private List<Appointment> appointments;

	private String imagefilepath;
	
	
	@OneToOne
	@JoinColumn(name = "doctoravilabilityid")
	private DoctorAvilability avilability;
	
	// Getters And Setters

	public List<Patient> getConsaltentpatients() {
		return consaltentpatients;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getExperiance() {
		return experiance;
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


	public List<Appointment> getAppointments() {
		return appointments;
	}


	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}


	public DoctorAvilability getAvilability() {
		return avilability;
	}


	public void setAvilability(DoctorAvilability avilability) {
		this.avilability = avilability;
	}


	public void setExperiance(int experiance) {
		this.experiance = experiance;
	}


	public String getImagefilepath() {
		return imagefilepath;
	}

	public void setImagefilepath(String imagefilepath) {
		this.imagefilepath = imagefilepath;
	}

	public String getDoctorid() {
		return doctorid;
	}

	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
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


//	@Override
//	public String toString() {
//		return "Doctor [doctorid=" + doctorid + ", name=" + name + ", specialization=" + specialization + ", age=" + age
//				+ ", gender=" + gender + ", number=" + number + ", email=" + email + ", address=" + address
//				+ ", salary=" + salary + ", noofcasesaccepted=" + noofcasesaccepted + ", noofcaseshold=" + noofcaseshold
//				+ ", totalrating=" + totalrating + ", rating=" + rating
//				+ ", consaltentpatients=" + consaltentpatients + ", imagefilepath=" + imagefilepath + "]";
//	}


}
