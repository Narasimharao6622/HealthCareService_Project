package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DoctorAvilability {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String doctoravilabilityid;
	
	@ManyToOne
	@JoinColumn(name = "doctorid")
	private Doctor doctor;
	
	private LocalTime starttime;
	private LocalTime endtime;
	
	private boolean available;

	private LocalDate availabledate;

	public String getDoctoravilabilityid() {
		return doctoravilabilityid;
	}

	public void setDoctoravilabilityid(String doctoravilabilityid) {
		this.doctoravilabilityid = doctoravilabilityid;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public LocalDate getAvailabledate() {
		return availabledate;
	}

	public void setAvailabledate(LocalDate availabledate) {
		this.availabledate = availabledate;
	}

	public LocalTime getStarttime() {
		return starttime;
	}

	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}

	public LocalTime getEndtime() {
		return endtime;
	}

	public void setEndtime(LocalTime endtime) {
		this.endtime = endtime;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	
}
