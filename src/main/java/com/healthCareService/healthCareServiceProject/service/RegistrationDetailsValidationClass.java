package com.healthCareService.healthCareServiceProject.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.healthCareService.healthCareServiceProject.exception.validation.EmailIdException;
import com.healthCareService.healthCareServiceProject.exception.validation.MobileNumberExcetion;
import com.healthCareService.healthCareServiceProject.exception.validation.PinCodeException;

@Component
public class RegistrationDetailsValidationClass {

	public long verifyMobilenumber(long mobilenumber) {
		String number = mobilenumber + "";
		int mobileNumberLength = number.length();
		if (mobileNumberLength == 10) {
			for (int i = 0; i <= mobileNumberLength - 1; i++) {
				char digit = number.charAt(i);
				if (i == 0) {
					if (!(digit == '9' || digit == '8' || digit == '7' || digit == '6')) {
						throw new MobileNumberExcetion("Starting number must be 9,8,7 or 6");
					}
				}
				if (digit <= '0' && digit >= '9') {
					throw new MobileNumberExcetion("invalid mobile number");
				}
			}
			return mobilenumber;
		} else if (mobileNumberLength == 0) {
			throw new MobileNumberExcetion("Mobile number can not be Zero");
		} else {
			throw new MobileNumberExcetion("Mobile number must be 10 digits");
		}
	}

	public long checkPinCode(long pincode) {
		System.out.println("Pincode validation...");
		String pin = pincode + "";
		int pincodeLingth = pin.length();
		if (pincodeLingth == 6) {
			for (int i = 0; i <= pincodeLingth - 1; i++) {
				char digit = pin.charAt(i);
				if (digit <= '0' && digit >= '9') {
					throw new PinCodeException("pin only contains digits...");
				}
				if (i == 0) {
					if (digit != '5') {
						throw new PinCodeException("pincode must be indian");
					}
				}
			}
			return pincode;
		}
		throw new PinCodeException("Please enter the valid pin code");
	}

	public String validateEmail(String email) {
		ArrayList<String> domains = new ArrayList<String>();
		domains.add("@gmail.com");
		domains.add("@yahoo.com");
		domains.add("@outlook.com");

		Optional<String> matchedDomain = domains.stream().filter(domain -> email.endsWith(domain)).findFirst();

		if (matchedDomain.isEmpty()) {
			throw new EmailIdException("Invalid email");
		}
		return email;

	}

}
