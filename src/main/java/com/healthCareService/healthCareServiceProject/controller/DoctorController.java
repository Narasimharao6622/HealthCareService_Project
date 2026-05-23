package com.healthCareService.healthCareServiceProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.dto.ApiResponse;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.exception.NoDoctorsFoundError;
import com.healthCareService.healthCareServiceProject.service.DoctorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/doctorController")
public class DoctorController {
	
	
	@Autowired
	private DoctorService service;
	
	@PostMapping(value="/saveDoctor", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> addDoctor(@RequestBody DoctorDTO requestdoctor,@RequestPart("photo") MultipartFile file) throws Exception {
		
		Doctor doctor = service.saveDoctorDetails(requestdoctor, file);
		if(doctor==null) {
			throw new NoDoctorsFoundError("registration failed....");
		}
		return ResponseEntity.ok(doctor);
	}
	
	@PutMapping("upadteDoctorProfileImage/{id}")
	public ResponseEntity<?> updateDoctorProfileImage(@RequestPart("photo") MultipartFile file,@PathVariable String id) {
		Doctor doctor = service.updateDoctorProfileImage(file, id);
		if(doctor!=null) {
			ApiResponse<?> response = new ApiResponse<>(200,"Profile Updated successful",null);
			return ResponseEntity.status(200).body(response);
		}
		throw new NoDoctorsFoundError("Doctor is Present by this id "+id);
	}
	
	
	
	
	
	
	
	
//    @PostMapping("/doctors")
//    public String addDoctor(@RequestPart("doctor") DoctorDTO requestdoctor,
//			@RequestPart("address") AddressDTO requestaddress,
//			@RequestPart("photo") MultipartFile file) throws Exception  {
//                ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//                Address address = context.getBean(Address.class);
//                address.setVillagename(requestaddress.getVillagename());
//                address.setMandal(requestaddress.getMandal());
//                address.setDistrict(requestaddress.getDistrict());
//                address.setState(requestaddress.getState());
//                address.setPincode(requestaddress.getPincode());
//                
//                
//                
//                Doctor doctor = context.getBean(Doctor.class);
//                doctor.setName(requestdoctor.getName());
//                doctor.setSpecialization(requestdoctor.getSpecialization());
//                doctor.setEmail(requestdoctor.getEmail());
//                doctor.setNumber(requestdoctor.getNumber());
//                doctor.setAge(requestdoctor.getAge());
//                doctor.setGender(requestdoctor.getGender());
//                doctor.setSalary(requestdoctor.getSalary());
//                doctor.setRating(requestdoctor.getRating());
//                doctor.setAddress(address);
//                
//                
//                
//                if (!file.isEmpty()) {
//     		       if(file.getSize()<=3 * 1024 * 1024) {
//     			        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//     			        String uploadDir = "src/main/resources/static/images/profiles/doctor";
//     			        File uploadPath = new File(uploadDir);
//     			        if (!uploadPath.exists()) {
//     			            uploadPath.mkdirs();
//     			        }
//     			        Path filePath = Paths.get(uploadDir + fileName);
//     			        Files.write(filePath, file.getBytes());
//     			        doctor.setImagefilepath("/images/profiles/" + fileName);
//     		       }else {
//     		    	   throw new Exception("File too large");
//     		       }
//     		    }
//        return "Doctor added successfully!";
//    }
}
