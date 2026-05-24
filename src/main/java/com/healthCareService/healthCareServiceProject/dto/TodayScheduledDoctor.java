package com.healthCareService.healthCareServiceProject.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TodayScheduledDoctor {
	private String name;
	private String department;
	private LocalDate todayDate;
	private List<LocalTime> timing;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public LocalDate getTodayDate() {
		return todayDate;
	}
	public void setTodayDate(LocalDate todayDate) {
		this.todayDate = todayDate;
	}
	public List<LocalTime> getTiming() {
		return timing;
	}
	public void setTiming(List<LocalTime> timing) {
		this.timing = timing;
	}	
	
	
}
