package com.healthCareService.healthCareServiceProject.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthCareService.healthCareServiceProject.dto.ApiResponse;
import com.healthCareService.healthCareServiceProject.dto.BookAppointmentRequest;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.dto.HomePagePatientResponse;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.exception.UserError;
import com.healthCareService.healthCareServiceProject.repository.PatientRepo;
import com.healthCareService.healthCareServiceProject.service.PatientService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/userController")
public class PatientController {

	@Autowired
	private PatientService service;

	@Autowired
	private PatientRepo repo;

	@GetMapping("/userHomePage")
	public ResponseEntity<ApiResponse<?>> userHomePage() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String id = auth.getName();
		Patient patient = repo.findById(id).orElseThrow(()->new UsernameNotFoundException("Login failed"));
		if(auth.isAuthenticated()) {
			HomePagePatientResponse userResponse = new HomePagePatientResponse(patient);
			ApiResponse<?> response = new ApiResponse<>(200, "User found", userResponse);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new UserError("Login failed...");
	}
	
	@GetMapping("/user_logout")
	public ResponseEntity<ApiResponse<?>> loginout(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("User-token","")
				.secure(false)
				.httpOnly(true)
				.sameSite("Lax")
				.path("/")
				.maxAge(0)
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		SecurityContextHolder.clearContext();
		ApiResponse<?> apiResponse = new ApiResponse<>(200, "logout successful", null);
		return ResponseEntity.status(200).body(apiResponse);
	}


	@PostMapping("/user_home_Page_SearchBar_ForOnlyUse_Doctors_Name")
	public ResponseEntity<ApiResponse<?>> searchDoctorByname(@RequestParam String name) {
		List<DoctorDTO> list = service.searchDoctorByname(name);
		ApiResponse<?> response = new ApiResponse<>(200, "success", list);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@GetMapping("/getDoctorAppoinments")
	public ResponseEntity<?> getPatientAppoinments() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String id = authentication.getName();
		ApiResponse<?> apiResponse = null;
		List<Doctor> list = service.getDoctorAppointments(id);
		// list.add(new Doctor());
		if(list.isEmpty()) {
			apiResponse = new ApiResponse<>(404, "No Appointments", null);
			return ResponseEntity.status(404).body(apiResponse);
		}
		apiResponse = new ApiResponse<>(200, "success", list);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@GetMapping("/getDoctorAppointmentTimingByUsingDate")
	public ResponseEntity<?> getDoctorAppointmentTimingByUsingDate(@RequestParam LocalDate date,@RequestParam String doctorid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Patient patient = repo.findById(auth.getName()).orElseThrow(()->new UsernameNotFoundException("Auth exception"));
		ApiResponse<?> apiResponse = new ApiResponse<>(200,"time slots",service.getDoctorAppointmentTimingByUsingDate(date,doctorid,patient)); 
		return ResponseEntity.ok().body(apiResponse);	
	}
	@PostMapping("/conformbookAppointment")
	public ResponseEntity<?> bookAppointment(@RequestBody BookAppointmentRequest bookAppointment) {
		System.out.println(bookAppointment);
		String patientid = SecurityContextHolder.getContext().getAuthentication().getName();
		ApiResponse<?> apiResponse = new ApiResponse<>(200,service.bookAppointment(bookAppointment,patientid),null);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@GetMapping("/getSpecialiazation")
	public ResponseEntity<?> getSpecialiazatioDoctors(@RequestParam String specialization) {
		List<DoctorDTO> getSpecializationDoctors = service.getSpecialiazatioDoctors(specialization);
		ApiResponse<?> apiResponse = new ApiResponse<>(200, "get doctors", getSpecializationDoctors);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@Cacheable("patient")
	@GetMapping("/getUserConsuntedDoctersDetails/{id}")
	public ResponseEntity<ApiResponse<?>> getUserConsuntedDoctersDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String patientid = auth.getName();
		ApiResponse<?> response = new ApiResponse<>(200, "Consultent Doctors",
				service.getUserConsultedDoctersDetails(patientid));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getDoctorByDoctorid")
	public ResponseEntity<?> getDoctorByDoctorid(@RequestParam String doctorid) {
		DoctorDTO dto = service.getDoctorByDoctorid(doctorid);
		ApiResponse<?> apiResponse = new ApiResponse<>(200,"Doctor Found",dto);
		return ResponseEntity.ok().body(apiResponse);
				
	}
	

	@GetMapping("/getAll")
	public List<Patient> getAllUsers() {
		System.out.println(repo.findAll());
		return repo.findAll();
	}
	

	
}