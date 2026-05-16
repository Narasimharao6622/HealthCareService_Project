package com.healthCareService.healthCareServiceProject.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WorkingDates {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int date_id;
	
	
	private LocalDate date;
	private List<LocalTime> workingtimes;
	
	
	
	@Override
	public String toString() {
		return "WorkingDates [id=" + date_id + ", date=" + date + ", workingtimes=" + workingtimes + "]";
	}
	public List<LocalTime> getWorkingtimes() {
		return workingtimes;
	}
	public void setWorkingtimes(List<LocalTime> workingtimes) {
		this.workingtimes = workingtimes;
	}
	public int getId() {
		return date_id;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
}
