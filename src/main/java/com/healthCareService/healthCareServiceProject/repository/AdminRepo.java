package com.healthCareService.healthCareServiceProject.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.healthCareService.healthCareServiceProject.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, String>{
	@Query("SELECT a FROM Admin a WHERE a.email = ?1")
	public Optional<Admin> findByEmail(String email);
	
	@Modifying
	@Transactional
	@Query("UPDATE Admin admin SET admin.name = :name , admin.gender = :gender , admin.dateofbirth = :dateOfBirth ,admin.email = :email , admin.mobilenumber = :mobilenumber WHERE admin.adminid = :adminid ")
	public int updateProfile(String name, String gender, LocalDate dateOfBirth, String email, long mobilenumber,String adminid);
}
