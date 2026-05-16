package com.healthCareService.healthCareServiceProject.exception;

public class NoDoctorsFoundError extends  RuntimeException{
	
	public NoDoctorsFoundError(String error) {
		super(error);
	}
}
