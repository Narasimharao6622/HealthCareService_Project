package com.healthCareService.healthCareServiceProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthCareService.healthCareServiceProject.entity.Appointment;

public interface AppointmentRepo extends JpaRepository<Appointment, String>{

}
