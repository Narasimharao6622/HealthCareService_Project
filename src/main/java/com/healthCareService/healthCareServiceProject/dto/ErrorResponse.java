package com.healthCareService.healthCareServiceProject.dto;

import java.util.List;

public class ErrorResponse <T>{
	private int status;
	private String message;
    private List<String> errors;
    private String path;
    private String timestamp;
    private boolean condition;
    
	public ErrorResponse() {
		super();
	}
	
	public boolean isCondition() {
		return condition;
	}

	public void setCondition(boolean condition) {
		this.condition = condition;
	}

	public ErrorResponse(int status, String message, List<String> errors, String path) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
		this.path = path;
		this.timestamp = java.time.LocalDateTime.now().toString();
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
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
    
    
}
