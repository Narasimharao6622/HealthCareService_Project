package com.healthCareService.healthCareServiceProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.configuration.AppConfig;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.entity.Address;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.exception.FileException;
import com.healthCareService.healthCareServiceProject.exception.NoDoctorsFoundError;
import com.healthCareService.healthCareServiceProject.exception.UserError;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepo repo;
	
	public Doctor saveDoctorDetails(DoctorDTO requestdoctor, MultipartFile file) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		Address address = context.getBean(Address.class);
		address.setHouse_no(requestdoctor.getAddress().getHouse_no());
		address.setStreet(requestdoctor.getAddress().getStreet());
		address.setLocation(requestdoctor.getAddress().getLocation());
		address.setCity(requestdoctor.getAddress().getCity());
		address.setDistrict(requestdoctor.getAddress().getDistrict());
		address.setState(requestdoctor.getAddress().getState());
		address.setPincode(Long.parseLong(requestdoctor.getAddress().getPincode()));
        
        
        
        Doctor doctor = context.getBean(Doctor.class);
        doctor.setName(requestdoctor.getName());
        doctor.setSpecialization(requestdoctor.getSpecialization());
        doctor.setEmail(requestdoctor.getEmail());
        doctor.setNumber(requestdoctor.getNumber());
        doctor.setAge(requestdoctor.getAge());
        doctor.setGender(requestdoctor.getGender());
        doctor.setSalary(requestdoctor.getSalary());
        doctor.setRating(requestdoctor.getRating());
        doctor.setAddress(address);
		
        if (!file.isEmpty()) {
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

			String uploadDir = "src/main/resources/static/images/profiles/";
			try {
				if (file.getSize() <= 3 * 1024 * 1024) {
					File uploadPath = new File(uploadDir);
					if (!uploadPath.exists()) {
						uploadPath.mkdirs();
					}
					Path filePath = Paths.get(uploadDir + fileName);
					Files.write(filePath, file.getBytes());
					doctor.setImagefilepath("/images/profiles/" + fileName);
				} else {
					throw new FileException("File too large");
				}
			}catch(IOException e) {
				throw new FileException(e.getMessage());
			}
			Doctor response = repo.save(doctor);
			System.out.println(response);
			if(response==null) {
				try {
					Path path = Paths.get(uploadDir+fileName);
					Files.delete(path);
					throw new UserError("user doesn't saved..");
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return doctor;
	}
	
	
	public Doctor updateDoctorProfileImage(MultipartFile file,int id) {
		Optional<Doctor> doctorIsPresent = repo.findById(id);
		if(doctorIsPresent.isPresent()) {
			Doctor doctor = doctorIsPresent.get();
			if (!file.isEmpty()) {
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

				String uploadDir = "src/main/resources/static/images/doctors/profiles/";
				try {
					if (file.getSize() <= 3 * 1024 * 1024) {
						File uploadPath = new File(uploadDir);
						if (!uploadPath.exists()) {
							uploadPath.mkdirs();
						}
						Path filePath = Paths.get(uploadDir + fileName);
						Files.write(filePath, file.getBytes());
						doctor.setImagefilepath("/images/doctors/profiles/" + fileName);
					} else {
						throw new FileException("File too large");
					}
				}catch(IOException e) {
					throw new FileException(e.getMessage());
				}
				Doctor response = repo.save(doctor);
				System.out.println(response);
				if(response==null) {
					try {
						Path path = Paths.get(uploadDir+fileName);
						Files.delete(path);
						throw new UserError("user doesn't saved..");
					}catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return doctor;
		}
		throw new NoDoctorsFoundError("Doctor is Present by this id "+id);
	}
}
