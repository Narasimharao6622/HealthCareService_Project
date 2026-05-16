package com.healthCareService.healthCareServiceProject.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.repository.AdminRepo;
import com.healthCareService.healthCareServiceProject.repository.PatientRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private PatientRepo repo;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Override
	public UserDetails loadUserByUsername(String value) throws UsernameNotFoundException {
		Optional<Patient> patientData = repo.findByEmail(value);
		if(patientData.isPresent()) {
			Patient patient = patientData.get();
			return User.withUsername(patient.getEmailid()).password(patient.getPassword()).roles(patient.getHasRole().name())
					.build();
		}
		Optional<Admin> adminData = adminRepo.findById(value);
		if(adminData.isPresent()) {
			Admin admin = adminData.get();
			return User.withUsername(admin.getAdminid()).password(admin.getPassword()).roles(admin.getHasRole().name())
					.build();
		}
		throw new UsernameNotFoundException("Invalid creditials..");	
		
	}

}
//		return new CustomUserDetails(patient);
