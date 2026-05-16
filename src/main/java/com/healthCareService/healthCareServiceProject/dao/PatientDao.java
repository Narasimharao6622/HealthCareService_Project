package com.healthCareService.healthCareServiceProject.dao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;

@Repository
public class PatientDao {
	
	@Autowired
	private DoctorRepo doctorRepo;
	
	public ArrayList<Doctor> getAllDoctors() {
		return doctorRepo.getAllDoctors();
	}
}
