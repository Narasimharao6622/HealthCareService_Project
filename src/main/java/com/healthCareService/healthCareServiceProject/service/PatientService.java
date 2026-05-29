package com.healthCareService.healthCareServiceProject.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.healthCareService.healthCareServiceProject.configuration.AppConfig;
import com.healthCareService.healthCareServiceProject.dao.PatientDao;
import com.healthCareService.healthCareServiceProject.dto.AddressDTO;
import com.healthCareService.healthCareServiceProject.dto.BookAppointmentRequest;
import com.healthCareService.healthCareServiceProject.dto.DoctorDTO;
import com.healthCareService.healthCareServiceProject.dto.PatientDTO;
import com.healthCareService.healthCareServiceProject.entity.Address;
import com.healthCareService.healthCareServiceProject.entity.Appointment;
import com.healthCareService.healthCareServiceProject.entity.AppointmentStatus;
import com.healthCareService.healthCareServiceProject.entity.AppointmentTimings;
import com.healthCareService.healthCareServiceProject.entity.Doctor;
import com.healthCareService.healthCareServiceProject.entity.Patient;
import com.healthCareService.healthCareServiceProject.entity.Roles;
import com.healthCareService.healthCareServiceProject.exception.AppointmentBookedException;
import com.healthCareService.healthCareServiceProject.exception.DateAndTimeException;
import com.healthCareService.healthCareServiceProject.exception.FileException;
import com.healthCareService.healthCareServiceProject.exception.NameNotFound;
import com.healthCareService.healthCareServiceProject.exception.NoDoctorsFoundError;
import com.healthCareService.healthCareServiceProject.exception.PasswordError;
import com.healthCareService.healthCareServiceProject.exception.UserError;
import com.healthCareService.healthCareServiceProject.exception.validation.EmailIdException;
import com.healthCareService.healthCareServiceProject.exception.validation.MobileNumberExcetion;
import com.healthCareService.healthCareServiceProject.repository.DoctorRepo;
import com.healthCareService.healthCareServiceProject.repository.PatientRepo;

@Service
public class PatientService {
	@Autowired
	private PatientDao dao;

	@Autowired
	private PatientRepo repo;

	@Autowired
	private DoctorRepo doctorRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private RegistrationDetailsValidationClass validationClass;

	public Patient saveUserDetails(PatientDTO requestpatient, AddressDTO requestaddress, MultipartFile file) {
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
		patient.setMobilenumber(
				checkMobileIsPresentInDB(validationClass.verifyMobilenumber(requestpatient.getMobilenumber())));
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
			} catch (IOException e) {
				throw new FileException(e.getMessage());
			}
			Patient response = repo.save(patient);
			if (response == null) {
				try {
					Path path = Paths.get(uploadDir + fileName);
					Files.delete(path);
					throw new UserError("user doesn't saved..");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return patient;
	}

	public Patient login(String email, String password) {
		Optional<Patient> dbPatient = repo.findByEmail(email);
		if (dbPatient.isPresent()) {
			Patient patient = dbPatient.get();
			if (encoder.matches(password, patient.getPassword())) {
				return patient;
			} else {
				throw new PasswordError("Incorrect Password");
			}
		}
		throw new EmailIdException("Incorrect Email id");
	}

	public String checkEmailidPresentInDB(String emailid) {
		Optional<Patient> user = repo.findByEmail(emailid);
		if (user.isPresent()) {
			throw new EmailIdException("Email id Already exisited...");
		}
		return emailid;
	}

	public long checkMobileIsPresentInDB(long verifyMobilenumber) {
		Optional<Patient> patient = repo.checkMobileNumber(verifyMobilenumber);
		if (patient.isPresent()) {
			throw new MobileNumberExcetion("Mobile number is already exist..");
		}
		return verifyMobilenumber;
	}

	public List<Doctor> getUserConsultedDoctersDetails(String patientid) {
		Optional<Patient> findPatient = repo.findById(patientid);
		ArrayList<Doctor> consultentDoctors = new ArrayList<Doctor>();
		if (findPatient.isEmpty()) {
			throw new UserError("User Not Found..");
		} else {
			Patient patient = findPatient.get();
			List<Doctor> getAllDoctors = patient.getConsaltantdoctors();
			ListIterator<Doctor> allDoctors = getAllDoctors.listIterator();
			if (!getAllDoctors.isEmpty()) {
				while (allDoctors.hasNext()) {
					Doctor doctor = allDoctors.next();
					doctor.setConsaltentpatients(null);
					consultentDoctors.add(doctor);
				}
			} else {
				throw new NoDoctorsFoundError("Petient is not consultent to any doctor");
			}
		}
		return consultentDoctors;

	}

	public List<Doctor> getDoctorAppointments(String id) {
		Patient patient = repo.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));

		return repo.getDoctorAppointments(patient.getPatientid());
	}

	public List<DoctorDTO> searchDoctorByname(String name) {
		ArrayList<Doctor> doctorsList = dao.getAllDoctors();
		List<DoctorDTO> finalList = new ArrayList<DoctorDTO>();
		if (name.length() == 0) {
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
						doctorDTO.setDoctorid(doctor.getDoctorid());
						doctorDTO.setName(doctor.getName());
						doctorDTO.setAge(doctor.getAge());
						doctorDTO.setGender(doctor.getGender());
						doctorDTO.setImagefilepath(doctor.getImagefilepath());
						doctorDTO.setSpecialization(doctor.getSpecialization());
						doctorDTO.setTotalrating(doctor.getTotalrating());

						if (word1.equals("Dr.")) {
							continue;
						} else {
							if (name.length() == 1) {
								if (Character.toLowerCase(name.charAt(0)) == Character.toLowerCase(word1.charAt(0))) {

									finalList.add(doctorDTO);
									break;
								}
							} else if (word1.toLowerCase().contains(name.toLowerCase())) {
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

	public boolean forgetPassword(String email) {
		Patient patient = repo.findEmail(email);
		if (patient != null) {
			return true;
		}
		return false;
	}

	public List<DoctorDTO> getSpecialiazatioDoctors(String specialization) {
		List<Doctor> doctorsList = dao.getAllDoctors();
		if (doctorsList.isEmpty()) {
			throw new NoDoctorsFoundError("DB is empty, No doctors are found...");
		} else {
			List<DoctorDTO> responseList = new LinkedList<DoctorDTO>();
			List<Doctor> list = doctorsList.stream()
					.filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specialization)).toList();
			if (list.isEmpty()) {
				throw new NoDoctorsFoundError("No doctors found for " + specialization);
			} else {
				list.stream().forEach(doctor -> {
					DoctorDTO doctorResponse = new DoctorDTO();
					doctorResponse.setName(doctor.getName());
					doctorResponse.setAge(doctor.getAge());
					doctorResponse.setGender(doctor.getGender());
					doctorResponse.setImagefilepath(doctor.getImagefilepath());
					doctorResponse.setNoofcasesaccepted(doctor.getNoofcasesaccepted());
					doctorResponse.setTotalrating(doctor.getTotalrating());
					doctorResponse.setNoofcaseshold(doctor.getNoofcaseshold());
					doctorResponse.setRating(doctor.getRating());
					doctorResponse.setExperiance(doctor.getExperiance());
					doctorResponse.setSpecialization(doctor.getSpecialization());
					doctorResponse.setDoctorid(doctor.getDoctorid());
					responseList.add(doctorResponse);
				});
			}
			return responseList;
		}
	}

	public DoctorDTO getDoctorByDoctorid(String doctorid) {
		Doctor doctor = dao.getDoctorByDoctorid(doctorid);
		DoctorDTO doctorResponse = new DoctorDTO();
		doctorResponse.setName(doctor.getName());
		doctorResponse.setAge(doctor.getAge());
		doctorResponse.setGender(doctor.getGender());
		doctorResponse.setImagefilepath(doctor.getImagefilepath());
		doctorResponse.setNoofcasesaccepted(doctor.getNoofcasesaccepted());
		doctorResponse.setTotalrating(doctor.getTotalrating());
		doctorResponse.setNoofcaseshold(doctor.getNoofcaseshold());
		doctorResponse.setRating(doctor.getRating());
		doctorResponse.setExperiance(doctor.getExperiance());
		doctorResponse.setSpecialization(doctor.getSpecialization());
		doctorResponse.setDoctorid(doctor.getDoctorid());

		return doctorResponse;

	}

	public String bookAppointment(BookAppointmentRequest request, String patientid) {
		System.out.println(request);
		Patient patient = repo.findById(patientid)
				.orElseThrow(() -> new UsernameNotFoundException("Patient not found"));
		Doctor doctor = doctorRepo.findById(request.getDoctorid())
				.orElseThrow(() -> new UsernameNotFoundException("Doctor not found"));

//		dao.existsByDoctorAndAppointmentDateAndSlotAndStatus(doctor, request);
//		dao.existsByPatientAndAppointmentDateAndSlotAndStatus(patient, request);
		
		try {
			Appointment appointment = new Appointment();
			appointment.setAppointmentdate(request.getAppointmentdate());
			appointment.setSlots(request.getSlot());
			appointment.setPatient(patient);
			appointment.setDoctor(doctor);
			appointment.setProblem(request.getSymptoms());
			appointment.setCreatedat(LocalDateTime.now());
			appointment.setConsultencyfee(request.getConsultancyFee());
			appointment.setStatus(AppointmentStatus.PENDING);
System.out.println(appointment);
			dao.bookAppointment(appointment);
			return "appointmentBooked";
		} catch (Exception e) {
			return "Some thing went to worng";
		}

	}

	public void checkEmailidRegistredOrNot(String email) {
		Optional<Patient> patient = repo.findByEmail(email);
		if (patient.isEmpty()) {
			throw new EmailIdException("Email not Registred Please Enter registred Email id");
		}
	}

	public boolean updatePasswordUsingEmail(String email, String userpassword) {
		String password = encoder.encode(userpassword);
		if (repo.updatePasswordUsingEmail(email, password) != 0) {
			return true;
		}
		return false;
	}

	public Map<String, String> getDoctorAppointmentTimingByUsingDate(LocalDate date, String doctorid,Patient patient) {
		if (date.isBefore(LocalDate.now())) {
			throw new DateAndTimeException("Past dates are not allowed");
		}

		Doctor doctor = doctorRepo.findById(doctorid)
				.orElseThrow(() -> new UsernameNotFoundException("No Doctor Found"));
		
		List<AppointmentStatus> statuses = List.of(
				AppointmentStatus.APPROVED,
				AppointmentStatus.PENDING
				);
		
		List<Appointment> bookedAppointments = dao.getDoctorAppointmentTimingByUsingDate(doctor, date, statuses);
		List<AppointmentTimings> bookedTiming = bookedAppointments.stream().map(appointment -> appointment.getSlots())
				.toList();

		List<AppointmentTimings> availableTimings = Arrays.stream(AppointmentTimings.values())
				.filter(slot -> !bookedTiming.contains(slot)).toList();
		if(availableTimings.isEmpty()) {
			throw new AppointmentBookedException("Today Doctor have no appointment times...");
		}
System.out.println(availableTimings+"===============================");
		Map<String, String> availableSlotTimes = new HashMap<String, String>();
		availableTimings.stream().forEach(slot -> {
			switch (slot.toString()) {
			case "SLOT_10_00": {
				availableSlotTimes.put(slot.toString(), "10:00AM to 10:30AM");
			}break;
			case "SLOT_11_00": {
				availableSlotTimes.put(slot.toString(), "11:00AM to 11:30AM");
			}break;
			case "SLOT_12_00": {
				availableSlotTimes.put(slot.toString(), "12:00AM to 12:30AM");
			}break;
			case "SLOT_02_00": {
				availableSlotTimes.put(slot.toString(), "02:00PM to 02:30PM");
			}break;
			case "SLOT_03_00": {
				availableSlotTimes.put(slot.toString(), "03:00PM to 03:30PM");
			}break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + slot.toString());
			}
		});
		return availableSlotTimes;
	}

}
