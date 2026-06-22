package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.PatientDTO;

public interface IPatientService {

    PatientDTO registerPatient(PatientDTO patientDTO);

    PatientDTO getPatientById(Integer patientId);

    List<PatientDTO> getAllPatients();

    PatientDTO updatePatient(Integer patientId, PatientDTO patientDTO);

    void deletePatient(Integer patientId);
}