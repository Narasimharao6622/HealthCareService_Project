package com.healthCareService.healthCareServiceProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.healthCareService.healthCareServiceProject.entity.Patient;


@Repository
public interface PatientRepo extends JpaRepository<Patient, Integer>{
	
	@Query("select patient from Patient patient where patient.mobilenumber = ?1")
	public Optional<Patient> checkMobileNumber(long mobilenumber);
	
	@Query("select patient from Patient patient where patient.emailid = ?1")
	public Patient findEmail(String email);
	
	@Query("SELECT p FROM Patient p WHERE p.emailid = :email OR p.mobilenumber = :mobile")
	public List<Optional<Patient>> findByEmailAndMobileNumber(@Param("email") String emailid,@Param("mobile") long mobilenumber);
	
	@Query("select p from Patient p where p.emailid = :email")
	public Optional<Patient> findByEmail(String email);

	
}
