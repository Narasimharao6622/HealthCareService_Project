package com.healthCareService.healthCareServiceProject.dto;

public class AddressDTO {
	
	private String house_no;
	private String street;
	private String city;
	private String location;
	private String district;
	private String state;
	private String country = "INDIA";
	private String pincode;
	
	
	
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



	public String getPincode() {
		return pincode;
	}



	public void setPincode(String pincode) {
		this.pincode = pincode;
	}



	@Override
	public String toString() {
		return "AddressDTO [house_no=" + house_no + ", street=" + street + ", city=" + city + ", location=" + location
				+ ", district=" + district + ", state=" + state + ", country=" + country + ", pincode=" + pincode + "]";
	}
	
}
