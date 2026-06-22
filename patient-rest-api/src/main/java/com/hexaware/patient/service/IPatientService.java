package com.hexaware.patient.service;

import java.util.List;

import com.hexaware.patient.dto.PatientDTO;
import com.hexaware.patient.entity.Patient;

public interface IPatientService {

	public Patient addPatient(PatientDTO patientDTO );
	public PatientDTO getById(int pid);
	public List<Patient> getAllPatients();
	public Patient updatePatient(PatientDTO patientDTO);
	
}
