package com.hexaware.careassist.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientInsuranceDTO {

	
	private int enrollmentId;
	private int patientId;
	private int planId;
	private LocalDate enrollmentDate;
	private LocalDate expiryDate;
	private BigDecimal coverageUsed;
	private String status;
	private String planName;
	private String companyName;
	private BigDecimal coverageAmount;
	private BigDecimal premium;
}
