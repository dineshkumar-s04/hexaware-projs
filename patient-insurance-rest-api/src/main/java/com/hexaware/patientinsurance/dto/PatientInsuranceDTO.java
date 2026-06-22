package com.hexaware.patientinsurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientInsuranceDTO {

	private int insuranceId;
	private String insuranceName;
	
	private int patientId; 
}
