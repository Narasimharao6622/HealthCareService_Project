package com.healthCareService.healthCareServiceProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCareService.healthCareServiceProject.entity.DoctorAvilability;

public interface DoctorAvilabilityRepo extends JpaRepository<DoctorAvilability, String>{

	public List<DoctorAvilability> findByDoctorDoctoridAndAvailabledate(String doctorid, LocalDate date);
}
