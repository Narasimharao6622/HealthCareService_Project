package com.healthCareService.healthCareServiceProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthCareService.healthCareServiceProject.Component.OTPStore;
import com.healthCareService.healthCareServiceProject.dto.OTPdto;


@Service
public class AppService {
	
	@Autowired
	private OTPStore store;
	
	public boolean verifyOTP(String email, String otp) {
		System.out.println(otp +" <- otp");
		OTPdto storedotp =store.getOtp(email);
		System.out.println(storedotp.getOtp() +" <- stored otp ");
		if(storedotp.getOtp().equals(otp)) {
			store.clearOtp(email);
			return true;
		}
		return false;
	}

}
