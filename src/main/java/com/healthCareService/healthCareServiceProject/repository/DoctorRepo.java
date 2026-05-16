package com.healthCareService.healthCareServiceProject.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthCareService.healthCareServiceProject.entity.Doctor;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Integer>{

	@Query("select d from Doctor d")
	public ArrayList<Doctor> getAllDoctors();
	
}
