package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Column;
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
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String appointmentid;
	
	@ManyToOne
	@JoinColumn(name = "doctorid")
	private Doctor doctor;
	
	@ManyToOne
	@JoinColumn(name = "patientid")
	private Patient patient;
	
	@Column(name = "Appointment_Date")
	private LocalDate appointmentdate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Slots")
	private AppointmentTimings slots;
	
	@Column(name = "Problem")
	private String problem;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "Status")
	private AppointmentStatus status;
	
	@Column(name = "Created_Date")
	private LocalDateTime createdate;

	@Column(name = "Consultency_Fee")
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

	public LocalDateTime getCreatedate() {
		return createdate;
	}

	public void setCreatedat(LocalDateTime createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentid=" + appointmentid + " appointmentdate=" + appointmentdate + ", slots=" + slots + ", problem=" + problem + ", status="
				+ status + ", createdat=" + createdate + ", consultencyfee=" + consultencyfee + "]";
	}
	
	
	
}
