package com.healthCareService.healthCareServiceProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.healthCareService.healthCareServiceProject.dto.ApiResponse;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.dto.HomePagePatientResponse;
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
		System.out.println(auth.getAuthorities());
		String email = auth.getName();
		Patient patient = repo.findEmail(email);
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		if (email != null) {
			List<DoctorDTO> list = service.searchDoctorByname(name);
			ApiResponse<?> response = new ApiResponse<>(200, "success", list);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		throw new UserError("Login Error...");
	}

	@Cacheable("patient")
	@GetMapping("/getUserConsuntedDoctersDetails/{id}")
	public ResponseEntity<ApiResponse<?>> getUserConsuntedDoctersDetails(@PathVariable int id) {
		ApiResponse<?> response = new ApiResponse<>(200, "Consultent Doctors",
				service.getUserConsultedDoctersDetails(id));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/getUser/{id}")
	public Patient getUser(@PathVariable int id) {
		return service.findById(id);
	}

	@GetMapping("/getAll")
	public List<Patient> getAllUsers() {
		System.out.println(repo.findAll());
		return repo.findAll();
	}

	
}