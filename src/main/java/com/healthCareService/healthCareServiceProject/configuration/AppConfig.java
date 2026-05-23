package com.healthCareService.healthCareServiceProject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.healthCareService.healthCareServiceProject.entity.Address;
import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;

@Configuration
public class AppConfig {

	private static Patient patient;
	
	@Bean
	@Lazy
	public Patient getPatient() {
			if(patient==null) {
			return new Patient();
		}
		return patient;
	}
	
	@Bean
	@Lazy
	public Address getAddress() {
		return new Address();
	}


	@Lazy
	@Bean
	public Doctor getDoctor() {
		return new Doctor();
	}
	
	@Lazy
	@Bean
	public Admin getAdmin() {
		return new Admin();
	}
	
//	@Bean
//    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
//        return builder -> {
//            builder.simpleDateFormat("hh:mm a");
//        };
//    }
	
//	@Bean
//	public AdminMapper getAdminMpper() {
//		return new AdminMapper();
//	}
}















