package com.healthCareService.healthCareServiceProject.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)	
	private String ratingid;
	private double rate;
	private String feedback;
	
	private String patientname;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctorid")
	private Doctor doctor;
	
	
	public String getRatingid() {
		return ratingid;
	}
	public void setRatingid(String ratingid) {
		this.ratingid = ratingid;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getPatient() {
		return patientname;
	}
	public void setPatient(String patientname) {
		this.patientname = patientname;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	@Override
	public String toString() {
		return "Rating [ratungid=" + ratingid + ", rate=" + rate + ", feedback=" + feedback + ", patientname="
				+ patientname + ", doctor=" + doctor + "]";
	}
	
	
	
}
