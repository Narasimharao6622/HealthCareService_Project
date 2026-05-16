package com.healthCareService.healthCareServiceProject.exception;

public class UserError extends RuntimeException{
	
	private String error;
	
	public UserError(String error) {
		this.error = error;
	}
	
	public String getUserError() {
		return error;
	}
}
