package com.healthCareService.healthCareServiceProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthCareService.healthCareServiceProject.entity.Appointment;
import com.healthCareService.healthCareServiceProject.entity.AppointmentStatus;
import com.healthCareService.healthCareServiceProject.entity.AppointmentTimings;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;

public interface AppointmentRepo extends JpaRepository<Appointment, String>{

	@Query("SELECT a FROM Appointment a WHERE a.appointmentdate = ?1")
	public List<Appointment> getAppointmentByDate(LocalDate appointmentdate);

	
	public boolean existsByDoctorAndAppointmentdateAndSlotsAndStatus(Doctor doctor,LocalDate date, AppointmentTimings slot,String string);


	public boolean existsByPatientAndAppointmentdateAndSlotsAndStatus(Patient patient,LocalDate date, AppointmentTimings slot,String string);

	public Appointment findByDoctorAndAppointmentdateAndSlots(Doctor doctor, LocalDate appointmentdate,AppointmentTimings slots);
	public Appointment findByPatientAndAppointmentdateAndSlots(Patient patient, LocalDate appointmentdate,AppointmentTimings slots);


	public List<Appointment> findByDoctorAndAppointmentdateAndStatusIn(Doctor doctor, LocalDate date,
			List<AppointmentStatus> statuses);


}
