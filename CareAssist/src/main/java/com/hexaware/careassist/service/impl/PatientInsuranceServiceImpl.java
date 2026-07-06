package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.PatientInsuranceDTO;
import com.hexaware.careassist.entity.InsurancePlan;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.entity.PatientInsurance;
import com.hexaware.careassist.exception.InsurancePlanNotFoundException;
import com.hexaware.careassist.exception.PatientInsuranceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.repository.InsurancePlanRepository;
import com.hexaware.careassist.repository.PatientInsuranceRepository;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.service.IPatientInsuranceService;

@Service
public class PatientInsuranceServiceImpl implements IPatientInsuranceService {

	private static final Logger logger = LoggerFactory.getLogger(PatientInsuranceServiceImpl.class);

	@Autowired
	private PatientInsuranceRepository patientInsuranceRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private InsurancePlanRepository insurancePlanRepository;

	@Override
	public PatientInsuranceDTO enrollPlan(PatientInsuranceDTO dto) {

		logger.info("Enrolling patient {} to insurance plan {}", dto.getPatientId(), dto.getPlanId());

		Patient patient = patientRepository.findById(dto.getPatientId()).orElseThrow(() -> {

			logger.warn("Patient not found with id {}", dto.getPatientId());

			return new PatientNotFoundException("Patient not found with ID: " + dto.getPatientId());
		});

		InsurancePlan plan = insurancePlanRepository.findById(dto.getPlanId()).orElseThrow(() -> {

			logger.warn("Insurance plan not found with id {}", dto.getPlanId());

			return new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + dto.getPlanId());
		});

		PatientInsurance enrollment = new PatientInsurance();

		enrollment.setPatient(patient);
		enrollment.setPlan(plan);
		enrollment.setEnrollmentDate(LocalDate.now());
		enrollment.setExpiryDate(LocalDate.now().plusYears(1));
		enrollment.setCoverageUsed(BigDecimal.ZERO);
		enrollment.setStatus("ACTIVE");

		PatientInsurance savedEnrollment = patientInsuranceRepository.save(enrollment);

		logger.info("Enrollment created successfully with id {}", savedEnrollment.getEnrollmentId());

		PatientInsuranceDTO responseDTO = new PatientInsuranceDTO();

		responseDTO.setEnrollmentId(savedEnrollment.getEnrollmentId());
		responseDTO.setPatientId(savedEnrollment.getPatient().getPatientId());
		responseDTO.setPlanId(savedEnrollment.getPlan().getPlanId());
		responseDTO.setEnrollmentDate(savedEnrollment.getEnrollmentDate());
		responseDTO.setExpiryDate(savedEnrollment.getExpiryDate());
		responseDTO.setCoverageUsed(savedEnrollment.getCoverageUsed());
		responseDTO.setStatus(savedEnrollment.getStatus());

		return responseDTO;
	}

	@Override
	public PatientInsuranceDTO getEnrollmentById(Integer enrollmentId) {

		logger.info("Fetching enrollment with id {}", enrollmentId);

		PatientInsurance enrollment = patientInsuranceRepository.findById(enrollmentId).orElseThrow(() -> {

			logger.warn("Enrollment not found with id {}", enrollmentId);

			return new PatientInsuranceNotFoundException("Enrollment not found with ID: " + enrollmentId);
		});

		PatientInsuranceDTO dto = new PatientInsuranceDTO();

		dto.setEnrollmentId(enrollment.getEnrollmentId());
		dto.setPatientId(enrollment.getPatient().getPatientId());
		dto.setPlanId(enrollment.getPlan().getPlanId());
		dto.setPlanName(enrollment.getPlan().getPlanName());

		dto.setCoverageAmount(enrollment.getPlan().getCoverageAmount());

		dto.setPremium(enrollment.getPlan().getPremium());

		dto.setCompanyName(
		    enrollment.getPlan()
		              .getCompany()
		              .getCompanyName()
		);
		dto.setEnrollmentDate(enrollment.getEnrollmentDate());
		dto.setExpiryDate(enrollment.getExpiryDate());
		dto.setCoverageUsed(enrollment.getCoverageUsed());
		dto.setStatus(enrollment.getStatus());

		return dto;
	}

	@Override
	public List<PatientInsuranceDTO> getAllEnrollments() {

		logger.info("Fetching all enrollments");

		List<PatientInsurance> enrollments = patientInsuranceRepository.findAll();

		List<PatientInsuranceDTO> dtoList = new ArrayList<>();

		for (PatientInsurance enrollment : enrollments) {

			PatientInsuranceDTO dto = new PatientInsuranceDTO();

			dto.setEnrollmentId(enrollment.getEnrollmentId());
			dto.setPatientId(enrollment.getPatient().getPatientId());
			dto.setPlanId(enrollment.getPlan().getPlanId());
			dto.setPlanName(enrollment.getPlan().getPlanName());

			dto.setCoverageAmount(enrollment.getPlan().getCoverageAmount());

			dto.setPremium(enrollment.getPlan().getPremium());

			dto.setCompanyName(
			    enrollment.getPlan()
			              .getCompany()
			              .getCompanyName()
			);
			dto.setEnrollmentDate(enrollment.getEnrollmentDate());
			dto.setExpiryDate(enrollment.getExpiryDate());
			dto.setCoverageUsed(enrollment.getCoverageUsed());
			dto.setStatus(enrollment.getStatus());

			dtoList.add(dto);
		}

		logger.info("Total enrollments fetched: {}", dtoList.size());

		return dtoList;
	}
	
	@Override
	public List<PatientInsuranceDTO> getEnrollmentsByPatientId(Integer patientId) {

	    logger.info("Fetching enrollments for patient {}", patientId);

	    List<PatientInsurance> enrollments =
	            patientInsuranceRepository.findByPatientPatientId(patientId);

	    List<PatientInsuranceDTO> dtoList = new ArrayList<>();

	    for (PatientInsurance enrollment : enrollments) {

	        PatientInsuranceDTO dto = new PatientInsuranceDTO();

	        dto.setEnrollmentId(enrollment.getEnrollmentId());
	        dto.setPatientId(enrollment.getPatient().getPatientId());
	        dto.setPlanId(enrollment.getPlan().getPlanId());
	        dto.setPlanName(enrollment.getPlan().getPlanName());

	        dto.setCoverageAmount(enrollment.getPlan().getCoverageAmount());

	        dto.setPremium(enrollment.getPlan().getPremium());

	        dto.setCompanyName(
	            enrollment.getPlan()
	                      .getCompany()
	                      .getCompanyName()
	        );
	        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
	        dto.setExpiryDate(enrollment.getExpiryDate());
	        dto.setCoverageUsed(enrollment.getCoverageUsed());
	        dto.setStatus(enrollment.getStatus());

	        dtoList.add(dto);
	    }

	    logger.info("Total enrollments found for patient {}: {}", patientId, dtoList.size());

	    return dtoList;
	}

	@Override
	public void cancelEnrollment(Integer enrollmentId) {

		logger.info("Cancelling enrollment with id {}", enrollmentId);

		PatientInsurance enrollment = patientInsuranceRepository.findById(enrollmentId).orElseThrow(() -> {

			logger.warn("Enrollment not found with id {}", enrollmentId);

			return new PatientInsuranceNotFoundException("Enrollment not found with ID: " + enrollmentId);
		});

		patientInsuranceRepository.delete(enrollment);

		logger.info("Enrollment cancelled successfully with id {}", enrollmentId);
	}
}