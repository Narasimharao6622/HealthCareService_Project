package com.healthCareService.healthCareServiceProject.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.Component.OTPStore;
import com.healthCareService.healthCareServiceProject.dto.AddressDTO;
import com.healthCareService.healthCareServiceProject.dto.ApiResponse;
import com.healthCareService.healthCareServiceProject.dto.LoginRequest;
import com.healthCareService.healthCareServiceProject.dto.PatientDTO;
import com.healthCareService.healthCareServiceProject.dto.admin.AdminLoginRequestDTO;
import com.healthCareService.healthCareServiceProject.dto.admin.AdminRegistrationRequest;
import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.exception.FileException;
import com.healthCareService.healthCareServiceProject.exception.OTPError;
import com.healthCareService.healthCareServiceProject.exception.UserError;
import com.healthCareService.healthCareServiceProject.mapper.AdminMapper;
import com.healthCareService.healthCareServiceProject.repository.AdminRepo;
import com.healthCareService.healthCareServiceProject.security.JWTService;
import com.healthCareService.healthCareServiceProject.service.AdminService;
import com.healthCareService.healthCareServiceProject.service.AppService;
import com.healthCareService.healthCareServiceProject.service.PatientService;
import com.healthCareService.healthCareServiceProject.service.RegistrationDetailsValidationClass;
import com.healthCareService.healthCareServiceProject.utility.OTPUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/appController")
public class AppController {

	@Autowired
	private AppService appService;

	@Autowired
	private OTPStore otpStore;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private AdminMapper mapper;
	
	@Autowired
	private RegistrationDetailsValidationClass validationClass;
	
	@PostMapping(value = "/patientDetails", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>> saveUserDetails(@RequestPart("patient") PatientDTO requestpatient,
			@RequestPart("address") AddressDTO requestaddress, @RequestPart("photo") MultipartFile file) {
		Patient patient = patientService.saveUserDetails(requestpatient, requestaddress, file);
		if(patient==null) {
			ApiResponse<?> response = new ApiResponse<>(201, "Registration failed", null);
			return ResponseEntity.status(404).body(response);
		}
		ApiResponse<?> response = new ApiResponse<>(201, "Registrated successfully", null);
		return ResponseEntity.status(201).body(response);
	}
	
	@PostMapping("/check-mobile-number")
	public ResponseEntity<ApiResponse<?>> checkMobileNumber(String mobilenumber) {
		long number = Long.parseLong(mobilenumber);
		patientService.checkMobileIsPresentInDB(number);
		ApiResponse<?> response = new ApiResponse<>();
		response.setCondition(false);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/check_User_email_status")
	public ResponseEntity<ApiResponse<?>> checkUserEmailStatus(@RequestParam String email) {
		String emailid = patientService.checkEmailidPresentInDB(email);
		ApiResponse<?> response = new ApiResponse<>(200, "CONTINUE ,No user found", emailid);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/user_login")
	public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest userDetails, HttpServletResponse response) {
		// validate user from database
		Patient patient = patientService.login(userDetails.getEmailid(), userDetails.getPassword());
		//create JWT token
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(patient.getPatientid(),userDetails.getPassword()));
		long expiryTime =  (System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
		long cookieAge = TimeUnit.DAYS.toSeconds(1);
		String token = "";
		if(userDetails.isRememberme()) {
			// 1 day + 31 days
			expiryTime = (System.currentTimeMillis() + TimeUnit.DAYS.toMillis(31));
			token = jwtService.generateToken(patient.getPatientid(),expiryTime);	
			cookieAge = TimeUnit.DAYS.toSeconds(31);
		}else 
			token = jwtService.generateToken(patient.getPatientid(),expiryTime);
		if(authentication.isAuthenticated()) {
			//create cookie for storing the token
			ResponseCookie cookies = ResponseCookie.from("User-token",token)
					.secure(false)
					.httpOnly(true)
					.sameSite("Lax")
					.path("/")
					.maxAge(cookieAge)
					.build();
			response.addHeader(
					HttpHeaders.SET_COOKIE,
					cookies.toString());
			ApiResponse<?> apiResponse = new ApiResponse<>(200, "login successful", token);
			return ResponseEntity.status(200).body(apiResponse);
		}
		ApiResponse<?> apiResponse = new ApiResponse<>(201, "Some thing is fishy..", null);
		return ResponseEntity.status(200).body(apiResponse);
	}
	@PostMapping(value = "/register" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<?>> adminRegistetion(@RequestPart("adminDTO") AdminRegistrationRequest adminDTO ,@RequestPart("photo") MultipartFile file,HttpServletResponse response) throws IOException {
		Admin admin = mapper.adminDTO_To_AdminEntity(adminDTO);
		if (!file.isEmpty()) {
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			String uploadDir = "src/main/resources/static/images/admin/profiles/";
			try {
				if (file.getSize() <= 3 * 1024 * 1024) {
					File uploadPath = new File(uploadDir);
					if (!uploadPath.exists()) {
						uploadPath.mkdirs();
					}
					Path filePath = Paths.get(uploadDir + fileName);
					Files.write(filePath, file.getBytes());
					admin.setImageURLPath(("/images/admin/profiles/" + fileName));
				} else 
					throw new FileException("File too large");
			}catch(IOException e) {
				throw new FileException(e.getMessage());
			}
			admin = adminRepo.save(admin);
			if(admin==null) {
				try {
					Path path = Paths.get(uploadDir+fileName);
					Files.delete(path);
					throw new UserError("user doesn't saved..");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ApiResponse<?> apiResponse = new ApiResponse<>(200,"Registrated successfully",adminDTO);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@PostMapping("/adminLogin")
	public ResponseEntity<ApiResponse<?>> adminLogin(@RequestBody AdminLoginRequestDTO adminRequest,HttpServletResponse response) throws IOException {
		adminService.login(adminRequest);
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(adminRequest.getAdminid(), adminRequest.getPassword()));
		
		String token =jwtService.generateToken(adminRequest.getAdminid(), System.currentTimeMillis() + TimeUnit.DAYS.toMillis(31));
		if(authentication.isAuthenticated()) {
			ResponseCookie cookie = ResponseCookie.from("admin-token",token)
					.httpOnly(true)
					.secure(false)
					.sameSite("Lax")
					.path("/")
					.maxAge(Duration.ofDays(7))
					.build();
			response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
			ApiResponse<?> apiResponse = new ApiResponse<>(200,"Login successfully",adminRequest);
			return ResponseEntity.status(200).body(apiResponse);
		}
		ApiResponse<?> apiResponse = new ApiResponse<>(401,"Invalid Credentials",null);
		return ResponseEntity.status(200).body(apiResponse);
	}
	
	@PostMapping("/forgetPassword")
	public ResponseEntity<?> forgetPassword(@RequestParam("email") String email){
		System.out.println(email);
		String otp = OTPUtil.generateOtp();
		otpStore.saveOtp(email, otp);
		ApiResponse<?> apiResponse = new ApiResponse<>(200,otp,null);; 
		apiResponse.setCondition(patientService.forgetPassword(email));
		return ResponseEntity.ok().body(apiResponse);
	}
	
	@PostMapping("/email_otp_send")
	public ResponseEntity<ApiResponse<?>> userEmailOTP(@RequestParam String email) {
		String otp = OTPUtil.generateOtp();
		otpStore.saveOtp(email, otp);
		ApiResponse<?> response = new ApiResponse<>(200,otp,null);
		response.setCondition(true);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/clearOTP")
	public String clearOTPAfterTimeOut(@RequestParam String email) {
		otpStore.clearOtp(email);
		return "OTP expired";
	}

	// Verify OTP
	@PostMapping("/email_otp_verify")
	public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestParam String email, @RequestParam String otp) {
		if(appService.verifyOTP(email, otp)) {
			ApiResponse<?> response = new ApiResponse<>(200,"verified",null);
			response.setCondition(true);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		throw new OTPError("Invalid otp");
	}
	
	
	@PostMapping("/send_mobile_otp")
	public ApiResponse<?> userMobileOTP(@RequestParam String number) {
		validationClass.verifyMobilenumber(Long.parseLong(number));
		String otp = OTPUtil.generateOtp();
		System.out.println(otp);
		otpStore.saveOtp(number+"", otp);
		ApiResponse<?> response = new ApiResponse<>(200,otp,null);
		return response;
		
		
	}
	
	@PostMapping("/verify_mobile_otp")
	public ApiResponse<?> verifyMobileOTP(@RequestParam String mobilenumber,@RequestParam String otp){
//		System.out.println(otp);
		if(appService.verifyOTP(mobilenumber, otp)) {
			ApiResponse<?> response = new ApiResponse<>(200,"Verfied",null);
			return response;
		}
		ApiResponse<?> response = new ApiResponse<>(404,"Invalid OTP",null);
		return response;
	}
	
	
	
	
	
	
}















