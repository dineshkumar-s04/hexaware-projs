package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.PatientDTO;
import com.hexaware.careassist.service.IPatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

	@Autowired
	private IPatientService patientService;

	@PostMapping
	public ResponseEntity<PatientDTO> registerPatient(@RequestBody PatientDTO patientDTO) {

		PatientDTO savedPatient = patientService.registerPatient(patientDTO);

		return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
	}

	@GetMapping("/{patientId}")
	public ResponseEntity<PatientDTO> getPatientById(@PathVariable Integer patientId) {

		PatientDTO patient = patientService.getPatientById(patientId);

		return ResponseEntity.ok(patient);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<PatientDTO> getPatientByEmail(
	        @PathVariable String email) {

	    PatientDTO patient = patientService.getPatientByEmail(email);

	    return ResponseEntity.ok(patient);
	}

	@GetMapping
	public ResponseEntity<List<PatientDTO>> getAllPatients() {

		List<PatientDTO> patients = patientService.getAllPatients();

		return ResponseEntity.ok(patients);
	}

	@PutMapping("/{patientId}")
	public ResponseEntity<PatientDTO> updatePatient(@PathVariable Integer patientId,
			@RequestBody PatientDTO patientDTO) {

		PatientDTO updatedPatient = patientService.updatePatient(patientId, patientDTO);

		return ResponseEntity.ok(updatedPatient);
	}

	@DeleteMapping("/{patientId}")
	public ResponseEntity<String> deletePatient(@PathVariable Integer patientId) {

		patientService.deletePatient(patientId);

		return ResponseEntity.ok("Patient deleted successfully");
	}
}