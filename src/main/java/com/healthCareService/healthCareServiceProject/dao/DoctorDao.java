package com.healthCareService.healthCareServiceProject.dao;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.entity.Appointment;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;

@Component
public class DoctorDao {
	
	@Autowired
	private DoctorRepo doctorRepo;

	public Doctor addDoctor(Doctor doctor) {
		Doctor dbDoctor = doctorRepo.findByEmail(doctor.getEmail());
		if(dbDoctor!=null) {
			return dbDoctor;
		}
		return doctorRepo.save(doctor);
	}

	public LinkedList<Doctor> getTodayScheduleDoctorsList(LocalDate todayDate) {
		List<Doctor> dbDoctors = doctorRepo.getAllDoctors();
		LinkedList<Doctor> getTodayScheduleDoctorsList_fromDB = new LinkedList<Doctor>();
		if(!dbDoctors.isEmpty()) {
			dbDoctors.stream().forEach(doctor->{
				List<Appointment> appointments = doctor.getAppointments();
				if(!appointments.isEmpty()) {
					appointments.stream().forEach(appointment->{
						if(appointment.getAppointmentdate().equals(todayDate)) {
							getTodayScheduleDoctorsList_fromDB.add(doctor);
						}
					});
				}
			});
		}
		return getTodayScheduleDoctorsList_fromDB;
	}

}
