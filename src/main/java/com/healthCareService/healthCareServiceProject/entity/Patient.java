package com.healthCareService.healthCareServiceProject.entity;

import java.util.List;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

@Cacheable
@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)	
	private String patientid;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int age;
	private String gender;
	@Column(unique = true, nullable = false)
	private String emailid;
	private String secondemailid;
	@Column(unique = true, nullable = false)
	private long mobilenumber;
	private long secondmobilenumber;
	private String bloodgroup;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id",nullable = false)
	private Address address;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Roles hasRole;
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(joinColumns = @JoinColumn(name = "patientid"),inverseJoinColumns = @JoinColumn(name = "doctorid"))
	private List<Doctor> consaltantdoctors;
	
	
	private String imagefilepath;

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Doctor> getConsaltantdoctors() {
		return consaltantdoctors;
	}

	public void setConsaltantdoctors(List<Doctor> consaltantdoctors) {
		this.consaltantdoctors = consaltantdoctors;
	}

	public String getImagefilepath() {
		return imagefilepath;
	}

	public void setImagefilepath(String imagefilepath) {
		this.imagefilepath = imagefilepath;
	}
	
	
	
	public Roles getHasRole() {
		return hasRole;
	}

	public void setHasRole(Roles hasRole) {
		this.hasRole = hasRole;
	}

	@Override
	public String toString() {
		return "Patient [patientid=" + patientid + ", name=" + name + ", age=" + age + ", gender=" + gender
				+ ", emailid=" + emailid + ", secondemailid=" + secondemailid + ", mobilenumber=" + mobilenumber
				+ ", secondmobilenumber=" + secondmobilenumber + ", bloodgroup=" + bloodgroup + ", address=" + address
				+ ", password=" + password + ", imagefilepath="
				+ imagefilepath + "]";
	}

	
}