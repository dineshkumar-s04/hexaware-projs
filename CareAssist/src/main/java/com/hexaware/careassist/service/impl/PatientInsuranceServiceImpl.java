package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private PatientInsuranceRepository patientInsuranceRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private InsurancePlanRepository insurancePlanRepository;

	@Override
	public PatientInsuranceDTO enrollPlan(PatientInsuranceDTO dto) {

		Patient patient = patientRepository.findById(dto.getPatientId())
				.orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + dto.getPatientId()));

		InsurancePlan plan = insurancePlanRepository.findById(dto.getPlanId()).orElseThrow(
				() -> new InsurancePlanNotFoundException("Insurance Plan not found with ID: " + dto.getPlanId()));

		PatientInsurance enrollment = new PatientInsurance();

		enrollment.setPatient(patient);
		enrollment.setPlan(plan);
		enrollment.setEnrollmentDate(dto.getEnrollmentDate());
		enrollment.setExpiryDate(dto.getExpiryDate());
		enrollment.setCoverageUsed(dto.getCoverageUsed());
		enrollment.setStatus(dto.getStatus());

		PatientInsurance savedEnrollment = patientInsuranceRepository.save(enrollment);

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

		PatientInsurance enrollment = patientInsuranceRepository.findById(enrollmentId).orElseThrow(
				() -> new PatientInsuranceNotFoundException("Enrollment not found with ID: " + enrollmentId));

		PatientInsuranceDTO dto = new PatientInsuranceDTO();

		dto.setEnrollmentId(enrollment.getEnrollmentId());
		dto.setPatientId(enrollment.getPatient().getPatientId());
		dto.setPlanId(enrollment.getPlan().getPlanId());
		dto.setEnrollmentDate(enrollment.getEnrollmentDate());
		dto.setExpiryDate(enrollment.getExpiryDate());
		dto.setCoverageUsed(enrollment.getCoverageUsed());
		dto.setStatus(enrollment.getStatus());

		return dto;
	}

	@Override
	public List<PatientInsuranceDTO> getAllEnrollments() {

		List<PatientInsurance> enrollments = patientInsuranceRepository.findAll();

		List<PatientInsuranceDTO> dtoList = new ArrayList<>();

		for (PatientInsurance enrollment : enrollments) {

			PatientInsuranceDTO dto = new PatientInsuranceDTO();

			dto.setEnrollmentId(enrollment.getEnrollmentId());
			dto.setPatientId(enrollment.getPatient().getPatientId());
			dto.setPlanId(enrollment.getPlan().getPlanId());
			dto.setEnrollmentDate(enrollment.getEnrollmentDate());
			dto.setExpiryDate(enrollment.getExpiryDate());
			dto.setCoverageUsed(enrollment.getCoverageUsed());
			dto.setStatus(enrollment.getStatus());

			dtoList.add(dto);
		}

		return dtoList;
	}

	@Override
	public void cancelEnrollment(Integer enrollmentId) {

		PatientInsurance enrollment = patientInsuranceRepository.findById(enrollmentId).orElseThrow(
				() -> new PatientInsuranceNotFoundException("Enrollment not found with ID: " + enrollmentId));

		patientInsuranceRepository.delete(enrollment);
	}

}
