package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ApiResponse<T> {
	private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;
    private List<T> objects;
    private boolean condition;

    public ApiResponse(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    
    
//	public ApiResponse( int status, String message, List<T> objects) {
//		super();
//		this.timestamp = LocalDateTime.now();
//		this.status = status;
//		this.message = message;
//		this.objects = objects;
//	}

	

	public List<T> getObjects() {
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



	public void setObjects(List<T> objects) {
		this.objects = objects;
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

    
}
