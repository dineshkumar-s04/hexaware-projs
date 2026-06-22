package com.hexaware.springrest.datajpa.service;

import java.util.List;

import com.hexaware.springrest.datajpa.PatientDTO;
import com.hexaware.springrest.datajpa.entity.Patient;

public interface IPatientService {

	public Patient addPatient(PatientDTO dto);

	public Patient updatePatient(PatientDTO dto);

	public PatientDTO getByPid(int patient_id);

	public void deleteByPid(int patient_id);

	public List<Patient> getAllPatients();

	public List<Patient> findByNameOrPatientId(String name, int patient_id);

	public List<Patient> findByName(String name);
	

	public List<Patient> getGender(String gender);

	
	public  List<Patient>  getAllSQL();

}
