package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.PatientInsuranceDTO;

public interface IPatientInsuranceService {

    PatientInsuranceDTO enrollPlan(
            PatientInsuranceDTO patientInsuranceDTO);

    PatientInsuranceDTO getEnrollmentById(
            Integer enrollmentId);

    List<PatientInsuranceDTO> getAllEnrollments();

    void cancelEnrollment(Integer enrollmentId);
    
    List<PatientInsuranceDTO> getEnrollmentsByPatientId(
            Integer patientId);
}