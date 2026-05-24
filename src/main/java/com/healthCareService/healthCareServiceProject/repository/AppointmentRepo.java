package com.healthCareService.healthCareServiceProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCareService.healthCareServiceProject.entity.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, String>{

	@Query("SELECT a FROM Appointment a WHERE a.appointmentdate = ?1")
	public List<Appointment> getAppointmentByDate(LocalDate appointmentdate);
	
}
