package com.healthCareService.healthCareServiceProject.exception;

public class PasswordError extends RuntimeException{

	public PasswordError(String error) {
		super(error);
	}
}
