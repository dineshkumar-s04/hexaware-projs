package com.hexaware.patientinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientPatientInsuranceVO {

	private PatientInsuranceDTO patientInsurance;
	private Patient patient;
	
	
}
