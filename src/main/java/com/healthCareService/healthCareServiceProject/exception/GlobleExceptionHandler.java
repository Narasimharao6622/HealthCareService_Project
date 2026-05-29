package com.healthCareService.healthCareServiceProject.exception;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.healthCareService.healthCareServiceProject.dto.ErrorResponse;
import com.healthCareService.healthCareServiceProject.exception.validation.EmailIdException;
import com.healthCareService.healthCareServiceProject.exception.validation.MobileNumberExcetion;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobleExceptionHandler {
	
	
	@ExceptionHandler(UserError.class)
	public ResponseEntity<ErrorResponse<?>> handleUserErrors(UserError ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>(
				400,
				"USER_DETAILS_ERROR",
				Arrays.asList(ex.getUserError()),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResponseEntity<ErrorResponse<?>> indexOutOfBoundsException(IndexOutOfBoundsException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>(
				400,
				"USER_DETAILS_NOT_FOUND",
				Arrays.asList("Server error due to the index out of bounds"),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(UserDetailsException.class)
	public ResponseEntity<ErrorResponse<?>> handleGlobleExcetion(UserDetailsException ex){
		String error = ex.getMessage();
		int status_Code = 0; 
		if("Mobile number already exsited".equals(error)) {
			status_Code = 40;
		}else if("email id already exsited..".equals(error)) {
			status_Code = 41;
		}else {
			status_Code = 42;
		}
		System.out.println("Hai");
		ErrorResponse<?> response = new ErrorResponse<>(
				status_Code,
				"USER_DETAILS_ERROR",
				Arrays.asList(ex.getMessage()),
                null);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(NameNotFound.class)
	public ResponseEntity<ErrorResponse<?>> handleNameNotFound(NameNotFound ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>(
				404,
				"NAME_NOT_FOUND",
				Arrays.asList(ex.getMessage()),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(OTPError.class)
	public ResponseEntity<ErrorResponse<?>> otpError(OTPError ex,HttpServletRequest request){
		ex.printStackTrace();
		ErrorResponse<?> errorResponse = new ErrorResponse<>(
				400,
				ex.getMessage(),
				null,
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
	}
	@ExceptionHandler(VerificationException.class)
	public ResponseEntity<ErrorResponse<?>> verificationError(VerificationException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>(
				400,
				"VERIFICATION ERROR",
				Arrays.asList(ex.getMessage()),
				request.getRequestURI());
		
		return new ResponseEntity<>(errorResponse,HttpStatus.FOUND);
	}
	
	@ExceptionHandler(NoDoctorsFoundError.class)
	public ResponseEntity<ErrorResponse<?>> noDoctorsFoundError(NoDoctorsFoundError ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>(
				404,
				"Doctor_Not_Found",
				Arrays.asList(ex.getMessage()),
				request.getRequestURI());
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(MobileNumberExcetion.class)
	public ResponseEntity<ErrorResponse<?>> handleMobileNumberException(MobileNumberExcetion ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setCondition(true);
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(403);
		return new ResponseEntity<>(errorResponse,HttpStatus.OK);
	}
	@ExceptionHandler(EmailIdException.class)
	public ResponseEntity<ErrorResponse<?>> handledEmailIdException(EmailIdException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setCondition(false);
		errorResponse.setStatus(406);
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.OK);
	}
	
	@ExceptionHandler(PasswordError.class)
	public ResponseEntity<ErrorResponse<?>> handledPasswordException(PasswordError ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(407);
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.OK);
	}
	@ExceptionHandler(InvalidAdminID.class)
	public ResponseEntity<ErrorResponse<?>> handledInvalidAdminID(InvalidAdminID ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(200));
	}
	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<ErrorResponse<?>> handledLoginFailed(LoginFailedException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(200));
	}
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse<?>> handledException(RuntimeException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(200));
	}
	@ExceptionHandler(AppointmentBookedException.class)
	public ResponseEntity<ErrorResponse<?>> handledAppointmentBookedException(AppointmentBookedException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setPath(request.getRequestURI());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(404));
	}
	@ExceptionHandler(DateAndTimeException.class)
	public ResponseEntity<ErrorResponse<?>> handledDateAndTimeException(DateAndTimeException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setPath(request.getRequestURI());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(404));
	}
	@ExceptionHandler(DoctorSaveException.class)
	public ResponseEntity<ErrorResponse<?>> handledDoctorSaveException(DoctorSaveException ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setPath(request.getRequestURI());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(404));
	}
	@ExceptionHandler(NoTodayScheduleDoctorsListNotFound.class)
	public ResponseEntity<ErrorResponse<?>> handledNoTodayScheduleDoctorsListNotFound(NoTodayScheduleDoctorsListNotFound ex,HttpServletRequest request){
		ErrorResponse<?> errorResponse = new ErrorResponse<>();
		errorResponse.setStatus(404);
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setPath(request.getRequestURI());
		return new ResponseEntity<>(errorResponse,HttpStatus.valueOf(404));
	}
}

























