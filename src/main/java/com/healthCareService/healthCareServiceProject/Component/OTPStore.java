package com.healthCareService.healthCareServiceProject.Component;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.dto.OTPdto;

@Component
public class OTPStore {
	private Map<String, OTPdto> otpMap = new HashMap<>();

    public void saveOtp(String domine, String otp) {
    	OTPdto otpDto = new OTPdto();
    	otpDto.setWhichDomin(domine);
    	otpDto.setOtp(otp);
    	otpDto.setTimeStamp(LocalTime.now());
    	otpDto.setTimeLimit(System.currentTimeMillis()+(5*60*1000));
        otpMap.put(domine, otpDto);
    }

    public OTPdto getOtp(String domine) {
        return otpMap.get(domine);
    }

    public void clearOtp(String domine) {
        otpMap.remove(domine);
    }
	
}
