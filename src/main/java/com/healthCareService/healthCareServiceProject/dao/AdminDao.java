package com.healthCareService.healthCareServiceProject.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.exception.NoDoctorsFoundError;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;
import com.healthCareService.healthCareServiceProject.repository.PatientRepo;

@Component
public class AdminDao {

	@Autowired
	private DoctorRepo doctorRepo;
	
	@Autowired
	private PatientRepo patientRepo;
	
	public ArrayList<Doctor> getAllDoctors_PresentIn_DB() {
		ArrayList<Doctor> list = doctorRepo.getAllDoctors();
		if(list.isEmpty()) {
			throw new NoDoctorsFoundError("In DB No doctors are present...");
		}
		return list;
	}

	public ArrayList<Doctor> getAllDoctors() {
		return doctorRepo.getAllDoctors();
	}

	public ArrayList<Patient> getAllPatients_presentIn_DB() {
		return patientRepo.getAllPatients();
	}

}
