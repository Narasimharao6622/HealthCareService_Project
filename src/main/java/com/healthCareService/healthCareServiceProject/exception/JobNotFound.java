package com.healthCareService.healthCareServiceProject.exception;

public class JobNotFound extends RuntimeException{

	public JobNotFound(String error) {
		super(error);
	}
}
