package com.healthCareService.healthCareServiceProject.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.dto.BookAppointmentRequest;
import com.healthCareService.healthCareServiceProject.entity.Appointment;
import com.healthCareService.healthCareServiceProject.entity.AppointmentStatus;
import com.healthCareService.healthCareServiceProject.entity.AppointmentTimings;
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

	public void bookAppointment(Appointment appointment) {
		appointmentRepo.save(appointment);
	}

	public List<Appointment> getDoctorAppointmentTimingByUsingDate(Doctor doctor, LocalDate date, List<AppointmentStatus> statuses) {
		return appointmentRepo.findByDoctorAndAppointmentdateAndStatusIn(doctor,date,statuses);
	}

	public void existsByDoctorAndAppointmentDateAndSlotAndStatus(Doctor doctor,BookAppointmentRequest request) {
		Appointment getDoctorAppointment = appointmentRepo.findByDoctorAndAppointmentdateAndSlots(
				doctor,
				request.getAppointmentdate(),
				request.getSlot()
				);
		if(getDoctorAppointment.getStatus() == AppointmentStatus.PENDING) {
			throw new AppointmentBookedException("In this time Doctor has having one appointment is Pending");
		}else if(getDoctorAppointment.getStatus() == AppointmentStatus.APPROVED) {
			throw new AppointmentBookedException("In this time Doctor having one appointment is Approved");
			
		}
		
//		boolean slotAlreadyBooked= appointmentRepo.existsByDoctorAndAppointmentdateAndSlotsAndStatus(
//				doctor,
//				request.getAppointmentdate(),
//				request.getSlot(),
//				"Booked"
//				);
//		if(!slotAlreadyBooked) {
//			throw new AppointmentBookedException("Doctor has having an appointment at this time ");
//		}
//		System.out.println(slotAlreadyBooked);
		
	}

	public void existsByPatientAndAppointmentDateAndSlotAndStatus(Patient patient, BookAppointmentRequest request) {
		
		Appointment getDoctorAppointment = appointmentRepo.findByPatientAndAppointmentdateAndSlots(patient,request.getAppointmentdate(),request.getSlot());
		
		System.out.println(getDoctorAppointment);
		if(getDoctorAppointment.getStatus() == AppointmentStatus.PENDING) {
			throw new AppointmentBookedException("In this time Doctor has having one appointment is Pending");
		}else if(getDoctorAppointment.getStatus() == AppointmentStatus.APPROVED) {
			throw new AppointmentBookedException("In this time Doctor having one appointment is Approved");
			
		}
		
//		boolean slotAlreadyBooked= appointmentRepo.existsByPatientAndAppointmentdateAndSlotsAndStatus(
//				patient,
//				request.getAppointmentdate(),
//				request.getSlot(),
//				"Booked"
//				);
//		if(!slotAlreadyBooked) {
//			throw new AppointmentBookedException("You having an appointment at this time ");
//		}
//		System.out.println(slotAlreadyBooked);
	}

	public List<Doctor> getSpecializationDoctors(String get_Specialization) {
		List<Doctor> getDoctors = doctorRepo.findBySpecialization(get_Specialization);
		return getDoctors;
	}
}
