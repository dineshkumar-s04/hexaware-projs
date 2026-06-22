package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.PatientInsuranceDTO;
import com.hexaware.careassist.service.IPatientInsuranceService;

@RestController
@RequestMapping("/api/enrollments")
public class PatientInsuranceController {

	@Autowired
	private IPatientInsuranceService patientInsuranceService;

	@PostMapping
	public ResponseEntity<PatientInsuranceDTO> enrollPlan(@RequestBody PatientInsuranceDTO patientInsuranceDTO) {

		PatientInsuranceDTO enrollment = patientInsuranceService.enrollPlan(patientInsuranceDTO);

		return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
	}

	@GetMapping("/{enrollmentId}")
	public ResponseEntity<PatientInsuranceDTO> getEnrollmentById(@PathVariable Integer enrollmentId) {

		PatientInsuranceDTO enrollment = patientInsuranceService.getEnrollmentById(enrollmentId);

		return ResponseEntity.ok(enrollment);
	}

	@GetMapping
	public ResponseEntity<List<PatientInsuranceDTO>> getAllEnrollments() {

		List<PatientInsuranceDTO> enrollments = patientInsuranceService.getAllEnrollments();

		return ResponseEntity.ok(enrollments);
	}

	@DeleteMapping("/{enrollmentId}")
	public ResponseEntity<String> cancelEnrollment(@PathVariable Integer enrollmentId) {

		patientInsuranceService.cancelEnrollment(enrollmentId);

		return ResponseEntity.ok("Enrollment cancelled successfully");
	}
}