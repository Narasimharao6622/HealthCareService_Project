package com.healthCareService.healthCareServiceProject.exception;

public class NoTodayScheduleDoctorsListNotFound extends RuntimeException{
	public NoTodayScheduleDoctorsListNotFound(String error) {
		super(error);
	}
}
