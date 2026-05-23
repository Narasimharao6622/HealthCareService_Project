package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BookAppointmentRequest {
	private String doctorid;
	private LocalDate appointmentdate;
	private LocalTime appointmenttime;
	private String symptoms;
	
	public String getDoctorid() {
		return doctorid;
	}
	public void setDoctorid(String doctorid) {
		this.doctorid = doctorid;
	}
	
	public LocalDate getAppointmentdate() {
		return appointmentdate;
	}
	public void setAppointmentdate(LocalDate appointmentdate) {
		this.appointmentdate = appointmentdate;
	}
	public LocalTime getAppointmenttime() {
		return appointmenttime;
	}
	public void setAppointmenttime(LocalTime appointmenttime) {
		this.appointmenttime = appointmenttime;
	}
	public String getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	@Override
	public String toString() {
		return "BookAppointmentRequest [doctorid=" + doctorid + ", appointmentdate=" + appointmentdate
				+ ", appointmenttime=" + appointmenttime + ", symptoms=" + symptoms + "]";
	}
	
	
	
}
