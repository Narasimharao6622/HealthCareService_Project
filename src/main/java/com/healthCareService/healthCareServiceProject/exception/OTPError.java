package com.healthCareService.healthCareServiceProject.exception;

public class OTPError extends RuntimeException{
	public OTPError(String error) {
		super(error);
	}
}
