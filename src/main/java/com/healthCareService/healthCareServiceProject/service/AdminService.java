package com.healthCareService.healthCareServiceProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.dao.AdminDao;
import com.healthCareService.healthCareServiceProject.dao.DoctorDao;
import com.healthCareService.healthCareServiceProject.dao.PatientDao;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.dto.PatientDTO;
import com.healthCareService.healthCareServiceProject.dto.TodayScheduledDoctor;
import com.healthCareService.healthCareServiceProject.dto.admin.AdminLoginRequestDTO;
import com.healthCareService.healthCareServiceProject.dto.admin.UpdateAdminRequest;
import com.healthCareService.healthCareServiceProject.entity.Admin;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.exception.DoctorSaveException;
import com.healthCareService.healthCareServiceProject.exception.FileException;
import com.healthCareService.healthCareServiceProject.exception.InvalidAdminID;
import com.healthCareService.healthCareServiceProject.exception.JobNotFound;
import com.healthCareService.healthCareServiceProject.exception.NoTodayScheduleDoctorsListNotFound;
import com.healthCareService.healthCareServiceProject.exception.NoUserFoundExepction;
import com.healthCareService.healthCareServiceProject.exception.PasswordError;
import com.healthCareService.healthCareServiceProject.interfaces.CalculateDoctorSalary;
import com.healthCareService.healthCareServiceProject.repository.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo repo;

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private DoctorDao doctorDao;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private RegistrationDetailsValidationClass validationClass;

	public AdminLoginRequestDTO login(AdminLoginRequestDTO adminRequest) {
		Admin admin = repo.findById(adminRequest.getAdminid())
				.orElseThrow(() -> new InvalidAdminID("Invalid Admin id"));
		if (encoder.matches(adminRequest.getPassword(), admin.getPassword())) {
			return adminRequest;
		}
		throw new PasswordError("Incorrect Password");
	}

	public UpdateAdminRequest update(UpdateAdminRequest adminRequest, String adminid) {
		String name = adminRequest.getName();
		String gender = adminRequest.getGender();
		LocalDate dateOfBirth = adminRequest.getDateofbirth();
		String email = adminRequest.getEmail();
		long mobilenumber = adminRequest.getMobilenumber();
		int result = repo.updateProfile(name, gender, dateOfBirth, email, mobilenumber, adminid);
		if (result != 0) {
			Admin admin = repo.findById(adminid).orElseThrow();
			adminRequest.setImageURLPath(admin.getImageURLPath());
			adminRequest.setJoinDate(admin.getJoinDate());
			adminRequest.setAdminid(adminid);
			return adminRequest;
		}
		throw new RuntimeException("Update failed");
	}

	public void addDoctor(DoctorDTO doctorRequest, MultipartFile file) {
		Doctor doctor = new Doctor();
		doctor.setName(doctorRequest.getName());
		doctor.setDateofbirth(doctorRequest.getDateofbirth());
		doctor.setAge(getAge(doctorRequest.getDateofbirth()));
		doctor.setGender(doctorRequest.getGender());
		doctor.setSpecialization(doctorRequest.getSpecialization());
		doctor.setEmail(validationClass.validateEmail(doctorRequest.getEmail()));
		doctor.setNumber(validationClass.verifyMobilenumber(doctorRequest.getNumber()));
		doctor.setJoindate(doctorRequest.getJoindate());
		doctor.setExperiance(doctorRequest.getExperiance());
		doctor.setPassword(encoder.encode(doctorRequest.getPassword()));
		doctor.setSalary(getSalary(doctorRequest.getSpecialization(), doctorRequest.getExperiance()));
		doctor.setDoctorid(genareteDoctorId(doctorRequest.getName()));
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
			} catch (IOException e) {
				throw new FileException(e.getMessage());
			}
			Doctor daoResponse = doctorDao.addDoctor(doctor);
			System.out.println("hai");
			if(daoResponse!=null){
				return;
			}else {
				System.out.println("Not save");
				try {
					Path path = Paths.get(uploadDir + fileName);
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		throw new DoctorSaveException("doctor doesn't saved..");
	}
	
	private String genareteDoctorId(String name) {
		Random random = new Random();
		long number = random.nextLong(9999);
		String nameArray[] = name.split(" ");
		String lastWord = nameArray[nameArray.length-1];
		return lastWord+number;
	}

	private int getAge(LocalDate dateofbirth) {
		LocalDate currentDate = LocalDate.now();
		return Period.between(dateofbirth, currentDate).getYears();
	}
	
	private double getSalary(String jobType,int experiance) {
		CalculateDoctorSalary doctorSalary = (job, exp) -> {
			double baseSalary;
			String lowerCase_Job = jobType.toLowerCase();
			switch (lowerCase_Job) {
			case "cardiology": {
				baseSalary = 12000.00;
			}break;
			case "neurology": {
				baseSalary = 18600.00;
			}break;
			case "dermatology": {
				baseSalary = 22000.00;
			}break;
			case "pediatrics": {
				baseSalary = 13700.00;
			}break;
			case "oncology": {
				baseSalary = 8500.00;
			}break;
			case "orthopedics": {
				baseSalary = 19000.00;
			}break;
			case "dentist": {
				baseSalary = 20000.00;
			}break;
			case "pulmonology": {
				baseSalary = 9700.00;
			}break;
			case "rheumatology": {
				baseSalary = 18200.00;
			}break;
			case "gastroenterology": {
				baseSalary = 8600.00;
			}break;
			case "endocrinology": {
				baseSalary = 21700.00;
			}break;
			case "medico": {
				baseSalary = 12000.00;
			}break;
			default:
				throw new JobNotFound("Unexpected value: " + job);
			}
			return baseSalary + (exp * 6536.86);
		};
		return doctorSalary.caclulateSalary(jobType, experiance);
	}

	public List<TodayScheduledDoctor> getTodayScheduleDoctorsList() {
		List<Doctor> list = doctorDao.getTodayScheduleDoctorsList(LocalDate.now());
		if(list.isEmpty()) {
			throw new NoTodayScheduleDoctorsListNotFound("Today No Doctors sre scheduled");
		}
		
		List<TodayScheduledDoctor> responseDoctor = new LinkedList<TodayScheduledDoctor>();
		list.stream().forEach(doctor->{
			TodayScheduledDoctor newDoctor = new TodayScheduledDoctor();
			newDoctor.setName(doctor.getName());
			newDoctor.setDepartment(doctor.getSpecialization());
			newDoctor.setTodayDate(LocalDate.now());
			responseDoctor.add(newDoctor);
		});
		return responseDoctor;
	}

	public List<DoctorDTO> getAllDoctors_PresentIn_DB() {
		ArrayList<Doctor> doctorsList_from_DB = adminDao.getAllDoctors_PresentIn_DB();
		List<DoctorDTO> doctors_responseList = new ArrayList<DoctorDTO>();
		doctorsList_from_DB.stream().forEach(doctor -> {
			DoctorDTO doctorDTO = new DoctorDTO();
			doctorDTO.setName(doctor.getName());
			doctorDTO.setAge(doctor.getAge());
			doctorDTO.setDoctorid(doctor.getDoctorid());
			doctorDTO.setAvilability(doctor.getAvilability());
			doctorDTO.setAppointments(doctor.getAppointments());
			doctorDTO.setExperiance(doctor.getExperiance());
			doctorDTO.setImagefilepath(doctor.getImagefilepath());
			doctorDTO.setGender(doctor.getGender());
			doctorDTO.setSpecialization(doctor.getSpecialization());
			doctorDTO.setTotalrating(doctor.getTotalrating());
			doctorDTO.setRating(doctor.getRating());
			doctorDTO.setNoofcasesaccepted(doctor.getNoofcasesaccepted());
			doctors_responseList.add(doctorDTO);
		});
		return doctors_responseList;
		
	}

	public long getAllDoctorsCount_fromDB() {
		ArrayList<Doctor> totalDoctors = adminDao.getAllDoctors();
		System.out.println(totalDoctors);
		long doctorsCount = totalDoctors.stream().count();
		return doctorsCount;
	}

	public List<PatientDTO> getAllPatients_presentIn_DB() {
		ArrayList<Patient> totalPatients = adminDao.getAllPatients_presentIn_DB();
		List<PatientDTO> patientList = new ArrayList<PatientDTO>();
		if(totalPatients.isEmpty()) {
			throw new NoUserFoundExepction("No user are present in db");
		}
		patientList.stream().forEach(patient -> {
			PatientDTO patientDTO = new PatientDTO();
			patientDTO.setName(patient.getName());
			patientList.add(patientDTO);
		});
		return patientList;
	}
	public long getAllPatientsCount_fromDB() {
		ArrayList<Patient> totalPatients = adminDao.getAllPatients_presentIn_DB();
		long doctorsCount = totalPatients.stream().count();
		return doctorsCount;
	}
	
}
