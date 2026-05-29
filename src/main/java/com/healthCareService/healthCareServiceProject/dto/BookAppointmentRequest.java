package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthCareService.healthCareServiceProject.entity.AppointmentTimings;

public class BookAppointmentRequest {
	private String doctorid;
	private LocalDate appointmentdate;
	private AppointmentTimings slot;
	private String symptoms;
	private double consultantionFee;
	
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
	
	public double getConsultancyFee() {
		return consultantionFee;
	}
	public void setConsultancyFee(double consultantionFee) {
		this.consultantionFee = consultantionFee;
	}
	public AppointmentTimings getSlot() {
		return slot;
	}
	public void setSlot(AppointmentTimings slot) {
		this.slot = slot;
	}
	public String getSymptoms() {
		return symptoms;
	}
	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}
	@Override
	public String toString() {
		return "BookAppointmentRequest [ appointmentdate=" + appointmentdate + ", slot="
				+ slot + ", symptoms=" + symptoms + ", consultantionFee=" + consultantionFee + "]";
	}


	
	
	
}
