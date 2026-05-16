package com.healthCareService.healthCareServiceProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.configuration.AppConfig;
import com.healthCareService.healthCareServiceProject.dao.PatientDao;
import com.healthCareService.healthCareServiceProject.dto.AddressDTO;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.dto.PatientDTO;
import com.healthCareService.healthCareServiceProject.entity.Address;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.entity.Roles;
import com.healthCareService.healthCareServiceProject.exception.FileException;
import com.healthCareService.healthCareServiceProject.exception.NameNotFound;
import com.healthCareService.healthCareServiceProject.exception.NoDoctorsFoundError;
import com.healthCareService.healthCareServiceProject.exception.UserError;
import com.healthCareService.healthCareServiceProject.exception.validation.EmailIdException;
import com.healthCareService.healthCareServiceProject.exception.validation.MobileNumberExcetion;
import com.healthCareService.healthCareServiceProject.repository.PatientRepo;

@Service
public class PatientService {
	@Autowired
	private PatientDao dao;

	@Autowired
	private PatientRepo repo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private RegistrationDetailsValidationClass validationClass;

	public Patient saveUserDetails(PatientDTO requestpatient,AddressDTO requestaddress,MultipartFile file) {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		Address address = context.getBean(Address.class);
		address.setHouse_no(requestaddress.getHouse_no());
		address.setStreet(requestaddress.getStreet());
		address.setLocation(requestaddress.getLocation());
		address.setCity(requestaddress.getCity());
		address.setDistrict(requestaddress.getDistrict());
		address.setState(requestaddress.getState());
		address.setPincode(validationClass.checkPinCode(Long.parseLong(requestaddress.getPincode())));

		Patient patient = context.getBean(Patient.class);
		patient.setName(requestpatient.getName());
		patient.setAge(requestpatient.getAge());
		patient.setGender(requestpatient.getGender());
		patient.setBloodgroup(requestpatient.getBloodgroup());
		patient.setMobilenumber(checkMobileIsPresentInDB(validationClass.verifyMobilenumber(requestpatient.getMobilenumber())));
		patient.setEmailid(checkEmailidPresentInDB(requestpatient.getEmailid()));
		patient.setSecondmobilenumber(validationClass.verifyMobilenumber(requestpatient.getSecondmobilenumber()));
		patient.setSecondemailid(requestpatient.getSecondemailid());
		patient.setPassword(encoder.encode(requestpatient.getPassword()));
		patient.setAddress(address);
		patient.setHasRole(Roles.USER);
		
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
					patient.setImagefilepath("/images/profiles/" + fileName);
				} else {
					throw new FileException("File too large");
				}
			}catch(IOException e) {
				throw new FileException(e.getMessage());
			}
			Patient response = repo.save(patient);
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
		return patient;
	}
	public Patient login(String userEmailid, String password) {
		Optional<Patient> ispatient = repo.findByEmail(userEmailid);
		if (ispatient.isPresent()) {
			Patient patient = ispatient.get();
			if (encoder.matches(password,patient.getPassword())) {
				return patient;
			} else {
				throw new UserError("Incorrect Password");
			}
		} else {
			throw new UserError("Incorrect Email id");
		}
	}

	public String checkEmailidPresentInDB(String emailid) {
		Optional<Patient> user = repo.findByEmail(emailid);
		if(user.isPresent()) {
			throw new EmailIdException("Email id Already exisited...");
		}
		return emailid;
	}

	public long checkMobileIsPresentInDB(long verifyMobilenumber) {
		Optional<Patient> patient = repo.checkMobileNumber(verifyMobilenumber);
		if(patient.isPresent()) {
			throw new MobileNumberExcetion("Mobile number is already exist..");
		}
		return verifyMobilenumber;
	}

	public List<Doctor> getUserConsultedDoctersDetails(int id) {
		
		Optional<Patient> findPatient = repo.findById(id);
	
		ArrayList<Doctor> consultentDoctors = new ArrayList<Doctor>();
		if(findPatient.isEmpty()) {
			throw new UserError("User Not Found..");
		}else {
			Patient patient = findPatient.get();
			List<Doctor> getAllDoctors = patient.getConsaltentdoctors();
			ListIterator<Doctor> allDoctors = getAllDoctors.listIterator();
			if (!getAllDoctors.isEmpty()) {
				while (allDoctors.hasNext()) {
					Doctor doctor = allDoctors.next();
					System.out.println(doctor);
					doctor.setConsaltentpatients(null);
					consultentDoctors.add(doctor);
				}
			} else {
				throw new NoDoctorsFoundError("Petient is not consultent to any doctor");
			}
		}
		return consultentDoctors;

	}
	public Patient findById(int id) {
		List<Patient> getAllUser = repo.findAll();
		ListIterator<Patient> allUsers = getAllUser.listIterator();
		if(getAllUser.isEmpty()) {
			throw new UserError("Database is empty");
		}else {
			while(allUsers.hasNext()) {
				Patient patient = allUsers.next();
				if(patient.getPatientid()==id) {
					patient.setConsaltentdoctors(getUserConsultedDoctersDetails(id));;
//					patient.setConsaltentdoctors(getUserConsuntedDoctersDetails(id));;
					return patient;
				}
			}
			throw new UserError("User Not Found..");
		}
	}

	public List<DoctorDTO> searchDoctorByname(String name) {
		ArrayList<Doctor> doctorsList = dao.getAllDoctors();
		List<DoctorDTO> finalList = new ArrayList<DoctorDTO>();
		if(name.length()==0) {
			throw new NameNotFound("Name cannot be empty");
		}
		if (!doctorsList.isEmpty()) {
			for (Doctor doctor : doctorsList) {
				String doctorNameFromDB = doctor.getName();
				String[] splitDBNameToWords = doctorNameFromDB.split(" ");
				int firstNameCount = 0;
				try {
					for (int i = 0; i <= splitDBNameToWords.length - 1; i++) {
						String word1 = splitDBNameToWords[i];
						DoctorDTO doctorDTO = new DoctorDTO();
						
						doctorDTO.setName(doctor.getName());
						doctorDTO.setAge(doctor.getAge());
						doctorDTO.setGender(doctor.getGender());
						doctorDTO.setImagefilepath(doctor.getImagefilepath());
						doctorDTO.setSpecialization(doctor.getSpecialization());
						doctorDTO.setTotalrating(doctor.getTotalrating());
						doctorDTO.setWorkingdates(doctor.getWorkingdates());
						
						if (word1.equals("Dr.")) {
							continue;
						} else {
							if(name.length()==1) {
								if(Character.toLowerCase(name.charAt(0)) == Character.toLowerCase(word1.charAt(0))){
									
									finalList.add(doctorDTO);
									break;
								}
							}
							else if(word1.toLowerCase().contains(name.toLowerCase())) {
								finalList.add(doctorDTO);
								break;
							}
						}
					}
				} catch (Exception ex) {
					if (finalList.isEmpty() && firstNameCount != 0) {
						throw new NameNotFound("Doctors are present but not this name ' " + name + " '");
					}
				}
			}
			if (finalList.isEmpty()) {
				throw new NameNotFound("No Doctor found by this name ' " + name + " '");
			}
		} else {
			throw new NameNotFound("No Doctors Found...");
		}

		return finalList;
	}


}
