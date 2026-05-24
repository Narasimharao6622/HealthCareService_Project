package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.healthCareService.healthCareServiceProject.entity.Doctor;

public class ApiResponse<T> {
	private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private List<?> objects;
    private boolean condition;
    List<TodayScheduledDoctor> todayScheduleDoctorsList;

    public ApiResponse(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
	public ApiResponse(LocalDateTime timestamp, int status, String message, T data, List<?> objects,
			boolean condition) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.data = data;
		this.objects = objects;
		this.condition = condition;
	}

	public List<?> getObjects() {
		return objects;
	}



	public boolean isCondition() {
		return condition;
	}



	public void setCondition(boolean condition) {
		this.condition = condition;
	}



	public ApiResponse() {
		
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<TodayScheduledDoctor> getTodayScheduleDoctorsList() {
		return todayScheduleDoctorsList;
	}

	public void setTodayScheduleDoctorsList(List<TodayScheduledDoctor> todayScheduleDoctorsList) {
		this.todayScheduleDoctorsList = todayScheduleDoctorsList;
	}


    
}
