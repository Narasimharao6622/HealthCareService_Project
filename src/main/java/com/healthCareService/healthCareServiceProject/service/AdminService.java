package com.healthCareService.healthCareServiceProject.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthCareService.healthCareServiceProject.dto.admin.AdminLoginRequestDTO;
import com.healthCareService.healthCareServiceProject.dto.admin.UpdateAdminRequest;
import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.exception.InvalidAdminID;
import com.healthCareService.healthCareServiceProject.exception.PasswordError;
import com.healthCareService.healthCareServiceProject.mapper.AdminMapper;
import com.healthCareService.healthCareServiceProject.repository.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo repo;
	
	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public AdminLoginRequestDTO login(AdminLoginRequestDTO adminRequest ) {
		Admin admin = repo.findById(adminRequest.getAdminid()).orElseThrow(()->new InvalidAdminID("Invalid Admin id"));
		if(encoder.matches(adminRequest.getPassword(), admin.getPassword())) {
			return adminRequest;
		}
		throw new PasswordError("Incorrect Password");
	}

	public UpdateAdminRequest update(UpdateAdminRequest adminRequest,String adminid) {
		String name = adminRequest.getName();
		String gender = adminRequest.getGender();
		LocalDate dateOfBirth  = adminRequest.getDateofbirth();
		String email = adminRequest.getEmail();
		long mobilenumber = adminRequest.getMobilenumber();
		int result = repo.updateProfile(name,gender,dateOfBirth,email,mobilenumber,adminid);
		if(result!=0) {
			Admin admin = repo.findById(adminid).orElseThrow();
			adminRequest.setImageURLPath(admin.getImageURLPath());
			adminRequest.setJoinDate(admin.getJoinDate());
			adminRequest.setAdminid(adminid);
			return adminRequest;
		}
		throw new RuntimeException("Update failed");
	}
}
