package com.healthCareService.healthCareServiceProject.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, String>{

	@Query("select d from Doctor d")
	public ArrayList<Doctor> getAllDoctors();

	@Query("SELECT d.consaltentpatients FROM Doctor d")
	public List<Patient> getPatientAppoinments();

	public Doctor findByEmail(String email);
}
