package com.healthCareService.healthCareServiceProject.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;

@Repository
public class PatientDao {
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	public ArrayList<Doctor> getAllDoctors() {
		return doctorRepo.getAllDoctors();
	}

	public List<Patient> getPatientAppoinments() {
		 return doctorRepo.getPatientAppoinments();
	}
}
