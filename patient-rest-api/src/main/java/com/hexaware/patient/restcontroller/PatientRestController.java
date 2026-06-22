package com.hexaware.patient.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.patient.dto.PatientDTO;
import com.hexaware.patient.entity.Patient;
import com.hexaware.patient.service.IPatientService;

@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

	@Autowired
	IPatientService service;
	
	@PostMapping("/add")
	public Patient addPatient(@RequestBody PatientDTO patientDTO) {

		return service.addPatient(patientDTO);
	}
	
	@PutMapping(value="/update",consumes = "application/json")
	public Patient updatePatient(@RequestBody PatientDTO patientDTO) {

		return service.updatePatient(patientDTO);
	}
	
	@GetMapping("/get/{pid}")
	public PatientDTO getById(@PathVariable int pid) {

		PatientDTO patientDTO = service.getById(pid);

		return patientDTO;
	}
	
	@GetMapping("/getAll")
	public List<Patient> getAllPatients(){
		return service.getAllPatients();
	}
	
}
