package com.healthCareService.healthCareServiceProject.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.dto.BookAppointmentRequest;
import com.healthCareService.healthCareServiceProject.entity.Appointment;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.exception.AppointmentBookedException;
import com.healthCareService.healthCareServiceProject.exception.NoDoctorsFoundError;
import com.healthCareService.healthCareServiceProject.repository.AppointmentRepo;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;

@Component	
public class PatientDao {
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private AppointmentRepo appointmentRepo;
	
	public ArrayList<Doctor> getAllDoctors() {
		return doctorRepo.getAllDoctors();
	}

	public List<Patient> getPatientAppoinments() {
		 return doctorRepo.getPatientAppoinments();
	}

	public Doctor getDoctorByDoctorid(String doctorid) {
		Optional<Doctor> dbDoctor =  doctorRepo.findById(doctorid);
		if(dbDoctor.isPresent()) {
			return dbDoctor.get();
		}
		throw new NoDoctorsFoundError("No Doctor Found...");
	}

	public void checkPatientHaveAnyAppointmentAreBookedOrNot(BookAppointmentRequest request) {
		List<Appointment> dbappointment = appointmentRepo.getAppointmentByDate(request.getAppointmentdate());
		if(!dbappointment.isEmpty()) {
			dbappointment.stream().forEach(data -> {
				System.out.println("\"Appointment Already Booked...\"");
				if(data.getAppointmenttime().equals(request.getAppointmenttime()) && data.getDoctor().getDoctorid().equals(request.getDoctorid())){
					throw new AppointmentBookedException("Appointment Already Booked...");
				}else if(data.getAppointmenttime().equals(request.getAppointmenttime()) && !(data.getDoctor().getDoctorid().equals(request.getDoctorid()))) {
					throw new AppointmentBookedException("You Already have an another appointment at this time - "+data.getAppointmenttime());
				}
			});
		}
		
	}

	public void bookAppointment(Appointment appointment) {
		appointmentRepo.save(appointment);
	}
}
