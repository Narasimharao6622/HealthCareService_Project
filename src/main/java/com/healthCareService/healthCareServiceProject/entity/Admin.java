package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.util.Random;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Admin {
	
	@Id
	private String adminid;
	
	private String name;
	private String gender;
	private LocalDate dateofbirth;
	private LocalDate joinDate;
	private String email;
	private long mobilenumber;
	private String password;
	private String imageURLPath;
	
	@Enumerated(EnumType.STRING)
	private Roles hasRole;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	public Admin(String name, String gender, LocalDate dateofbirth, LocalDate joinDate, String email,
			long mobilenumber, String password, String imageURLPath) {
		this.name = name;
		this.gender = gender;
		this.dateofbirth = dateofbirth;
		this.joinDate = joinDate;
		this.email = email;
		this.mobilenumber = mobilenumber;
		this.password = password;
		this.imageURLPath = imageURLPath;
		this.setHasRole(Roles.ADMIN);
		this.adminid = genarateAdminID();
	}
	
	private String genarateAdminID() {
		String[] subPartsArrayName = name.split(" ");
		String id = "";
		String word = subPartsArrayName[subPartsArrayName.length-1];
		for(int i=0;i<=word.length()-1;i++) {
			char ch = word.charAt(i);
			if(i==0) {
				if(ch >= 'a' && ch <= 'z') {
				    ch = (char)(ch - 32);
				}
				id = id+ch;
			}else {
				id = id+ch;
			}
		}
		
		Random random = new Random();
		
		return id + random.nextLong(9999);
	}
	

	public Admin() {
		super();
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getAdminid() {
		return adminid;
	}
	
	

	public Roles getHasRole() {
		return hasRole;
	}

	public void setHasRole(Roles hasRole) {
		this.hasRole = hasRole;
	}

	@Override
	public String toString() {
		return "Admin [adminid=" + adminid + ", name=" + name + ", gender=" + gender + ", dateofbirth=" + dateofbirth
				+ ", joinDate=" + joinDate + ", email=" + email + ", mobilenumber=" + mobilenumber + ", password="
				+ password + ", imageURLPath=" + imageURLPath + ", address=" + address + "]";
	}
	
	
}
