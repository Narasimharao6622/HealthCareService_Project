package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.util.Random;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "AdminTable")
@Entity
public class Admin {
	
	@Id
	@Column(name = "AdminID")
	private String adminid;
	
	@Column(name = "Name" , nullable = false)
	private String name;
	
	@Column(name = "Gender")
	private String gender;
	
	@Column(name = "Date_Of_Birth")
	private LocalDate dateofbirth;
	
	@Column(name = "Join_Date")
	private LocalDate joinDate;
	
	@Column(name = "Email" , nullable = false)
	private String email;
	
	@Column(name = "Mobile_Number", nullable = false)
	private long mobilenumber;
	
	@Column(name = "Password", nullable = false)
	private String password;
	
	@Column(name = "Image_URL")
	private String imageURLPath;
	
	@Column(name = "Role")
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
