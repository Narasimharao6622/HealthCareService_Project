package com.healthCareService.healthCareServiceProject.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.dto.admin.AdminRegistrationRequest;
import com.healthCareService.healthCareServiceProject.entity.Admin;
	
@Component
public class AdminMapper {
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public Admin adminDTO_To_AdminEntity(AdminRegistrationRequest adminDTO) {
		return new Admin(adminDTO.getName(), adminDTO.getGender(), adminDTO.getDateofbirth(), adminDTO.getJoinDate(), adminDTO.getEmail(), adminDTO.getMobilenumber(),encoder.encode(adminDTO.getPassword()), null);
	}
}
