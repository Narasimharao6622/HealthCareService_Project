package com.healthCareService.healthCareServiceProject.exception;

public class LoginFailedException extends RuntimeException{
	 public LoginFailedException(String error) {
		super(error);
	}
}
