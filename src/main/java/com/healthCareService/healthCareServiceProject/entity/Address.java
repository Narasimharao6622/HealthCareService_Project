package com.healthCareService.healthCareServiceProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Address")
@Entity
public class Address {
	@Id
	@Column(name = "AddressID")
	@GeneratedValue(strategy = GenerationType.UUID)	
	private String address_id;
	
	@Column(name = "House_No")
	private String house_no;
	@Column(name = "Street")
	private String street;
	@Column(name = "City")
	private String city;
	@Column(name = "Location")
	private String location;
	@Column(name = "District")
	private String district;
	@Column(name = "State")
	private String state;
	@Column(name = "Country")
	private String country = "INDIA";
	@Column(name = "PinCode")
	private long pincode;
	
	public String getAddress_id() {
		return address_id;
	}
	public String getHouse_no() {
		return house_no;
	}
	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getPincode() {
		return pincode;
	}
	public void setPincode(long l) {
		this.pincode = l;
	}
	
	
}
