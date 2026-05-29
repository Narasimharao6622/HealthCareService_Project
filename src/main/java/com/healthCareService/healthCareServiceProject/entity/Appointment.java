package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String appointmentid;
	
	@ManyToOne
	@JoinColumn(name = "doctorid")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "patientid")
	private Patient patient;
	
	private LocalDate appointmentdate;
	
	@Enumerated(EnumType.STRING)
	private AppointmentTimings slots;
	
	private String problem;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;
	
	private LocalDateTime createdat;

	private double consultencyfee;
	
	public double getConsultencyfee() {
		return consultencyfee;
	}

	public void setConsultencyfee(double consultencyfee) {
		this.consultencyfee = consultencyfee;
	}

	public String getAppointmentid() {
		return appointmentid;
	}

	public void setAppointmentid(String appointmentid) {
		this.appointmentid = appointmentid;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public LocalDate getAppointmentdate() {
		return appointmentdate;
	}

	public void setAppointmentdate(LocalDate appointmentdate) {
		this.appointmentdate = appointmentdate;
	}


	public AppointmentTimings getSlots() {
		return slots;
	}

	public void setSlots(AppointmentTimings slots) {
		this.slots = slots;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedat() {
		return createdat;
	}

	public void setCreatedat(LocalDateTime createdat) {
		this.createdat = createdat;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentid=" + appointmentid + " appointmentdate=" + appointmentdate + ", slots=" + slots + ", problem=" + problem + ", status="
				+ status + ", createdat=" + createdat + ", consultencyfee=" + consultencyfee + "]";
	}
	
	
	
}
