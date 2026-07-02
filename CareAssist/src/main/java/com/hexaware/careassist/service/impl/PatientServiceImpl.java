package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.PatientDTO;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.IPatientService;

@Service
public class PatientServiceImpl implements IPatientService {

	private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public PatientDTO registerPatient(PatientDTO patientDTO) {

		logger.info("Registering patient for user id {}", patientDTO.getUserId());

		User user = userRepository.findById(patientDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", patientDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + patientDTO.getUserId());
		});

		Patient patient = new Patient();

		patient.setUser(user);
		patient.setDob(patientDTO.getDob());
		patient.setGender(patientDTO.getGender());
		patient.setSymptoms(patientDTO.getSymptoms());
		patient.setTreatment(patientDTO.getTreatment());
		patient.setAddress(patientDTO.getAddress());

		Patient savedPatient = patientRepository.save(patient);

		logger.info("Patient registered successfully with id {}", savedPatient.getPatientId());

		PatientDTO responseDTO = new PatientDTO();

		responseDTO.setPatientId(savedPatient.getPatientId());
		responseDTO.setUserId(savedPatient.getUser().getUserId());
		responseDTO.setDob(savedPatient.getDob());
		responseDTO.setGender(savedPatient.getGender());
		responseDTO.setSymptoms(savedPatient.getSymptoms());
		responseDTO.setTreatment(savedPatient.getTreatment());
		responseDTO.setAddress(savedPatient.getAddress());

		return responseDTO;
	}

	@Override
	public PatientDTO getPatientById(Integer patientId) {

		logger.info("Fetching patient with id {}", patientId);

		Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {

			logger.warn("Patient not found with id {}", patientId);

			return new PatientNotFoundException("Patient not found with ID: " + patientId);
		});

		PatientDTO patientDTO = new PatientDTO();

		patientDTO.setPatientId(patient.getPatientId());
		patientDTO.setUserId(patient.getUser().getUserId());
		patientDTO.setDob(patient.getDob());
		patientDTO.setGender(patient.getGender());
		patientDTO.setSymptoms(patient.getSymptoms());
		patientDTO.setTreatment(patient.getTreatment());
		patientDTO.setAddress(patient.getAddress());

		return patientDTO;
	}

	@Override
	public List<PatientDTO> getAllPatients() {

		logger.info("Fetching all patients");

		List<Patient> patients = patientRepository.findAll();

		List<PatientDTO> patientDTOList = new ArrayList<>();

		for (Patient patient : patients) {

			PatientDTO patientDTO = new PatientDTO();

			patientDTO.setPatientId(patient.getPatientId());
			patientDTO.setUserId(patient.getUser().getUserId());
			patientDTO.setDob(patient.getDob());
			patientDTO.setGender(patient.getGender());
			patientDTO.setSymptoms(patient.getSymptoms());
			patientDTO.setTreatment(patient.getTreatment());
			patientDTO.setAddress(patient.getAddress());

			patientDTOList.add(patientDTO);
		}

		logger.info("Total patients fetched: {}", patientDTOList.size());

		return patientDTOList;
	}

	@Override
	public PatientDTO updatePatient(Integer patientId, PatientDTO patientDTO) {

		logger.info("Updating patient with id {}", patientId);

		Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {

			logger.warn("Patient not found with id {}", patientId);

			return new PatientNotFoundException("Patient not found with ID: " + patientId);
		});

		User user = userRepository.findById(patientDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", patientDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + patientDTO.getUserId());
		});

		patient.setUser(user);
		patient.setDob(patientDTO.getDob());
		patient.setGender(patientDTO.getGender());
		patient.setSymptoms(patientDTO.getSymptoms());
		patient.setTreatment(patientDTO.getTreatment());
		patient.setAddress(patientDTO.getAddress());

		Patient updatedPatient = patientRepository.save(patient);

		logger.info("Patient updated successfully with id {}", updatedPatient.getPatientId());

		PatientDTO responseDTO = new PatientDTO();

		responseDTO.setPatientId(updatedPatient.getPatientId());
		responseDTO.setUserId(updatedPatient.getUser().getUserId());
		responseDTO.setDob(updatedPatient.getDob());
		responseDTO.setGender(updatedPatient.getGender());
		responseDTO.setSymptoms(updatedPatient.getSymptoms());
		responseDTO.setTreatment(updatedPatient.getTreatment());
		responseDTO.setAddress(updatedPatient.getAddress());

		return responseDTO;
	}

	@Override
	public void deletePatient(Integer patientId) {

		logger.info("Deleting patient with id {}", patientId);

		Patient patient = patientRepository.findById(patientId).orElseThrow(() -> {

			logger.warn("Patient not found with id {}", patientId);

			return new PatientNotFoundException("Patient not found with ID: " + patientId);
		});

		patientRepository.delete(patient);

		logger.info("Patient deleted successfully with id {}", patientId);
	}
}