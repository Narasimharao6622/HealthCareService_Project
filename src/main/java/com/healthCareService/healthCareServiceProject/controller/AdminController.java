package com.healthCareService.healthCareServiceProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.dto.ApiResponse;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.dto.admin.UpdateAdminRequest;
import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.exception.InvalidAdminID;
import com.healthCareService.healthCareServiceProject.exception.LoginFailedException;
import com.healthCareService.healthCareServiceProject.repository.AdminRepo;
import com.healthCareService.healthCareServiceProject.service.AdminService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/adminController")
public class AdminController {
	
	@Autowired
	private AdminRepo repo;
	@Autowired 
	private AdminService adminService;
		
	@GetMapping("/adminHomePage")
	public ResponseEntity<?> adminHomePage(HttpServletResponse httpServletResponse) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String adminid = authentication.getName();	
		Admin admin = repo.findById(adminid).orElseThrow(()->new InvalidAdminID("Login failed..."));
		if(authentication.isAuthenticated()) {
			ApiResponse<?> apiResponse = new ApiResponse<>(200,"Login successfull",admin);
			return ResponseEntity.status(200).body(apiResponse);
		}
		throw new LoginFailedException("Login failed..");
	}
	
	@PutMapping("/updateAdminProfile")
	public ResponseEntity<?> updateProfile(@RequestBody UpdateAdminRequest adminRequest) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String adminid = authentication.getName();
		ApiResponse<?> apiResponse = new ApiResponse<>(200,"update is successfull",adminService.update(adminRequest,adminid));
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@PostMapping("/addDoctor")
	public ResponseEntity<?> addDoctor(@RequestPart("doctor") DoctorDTO doctorRequest, @RequestPart("doctorImage") MultipartFile photo) {
		adminService.addDoctor(doctorRequest,photo);
		ApiResponse<?> apiResponse = new ApiResponse<>(200,"Add successfull",null);
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@GetMapping("/adminLogout")
	public ResponseEntity<?> adminLoginOut(HttpServletResponse httpServletResponse) {
		ResponseCookie cookie = ResponseCookie.from("admin-token","")
				.sameSite("Lax")
				.secure(true)
				.maxAge(0)
				.path("/")
				.build();
		httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		ApiResponse<?> apiResponse = new ApiResponse<>(200, "logout successful", null);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@GetMapping("/getTodayScheduleDoctorsList")
	public ResponseEntity<?> getTodayScheduleDoctorsList() {
		ApiResponse<?> apiResponse = new ApiResponse<>();
		apiResponse.setTodayScheduleDoctorsList(adminService.getTodayScheduleDoctorsList());
		apiResponse.setCondition(true);
		apiResponse.setStatus(200);
		apiResponse.setMessage("Today some Schedules are present...");
		return ResponseEntity.status(200).body(apiResponse);
	}
}

