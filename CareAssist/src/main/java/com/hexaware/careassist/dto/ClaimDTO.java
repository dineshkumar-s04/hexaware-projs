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
public class ClaimDTO {

	
	private int claimId;
	private int patientId;
	private int invoiceId;
	private int companyId;

	private BigDecimal claimAmount;

	private String diagnosis;
	private String treatmentDetails;

	private LocalDate claimDate;

	private String status;

	private LocalDate approvedDate;
	private String rejectionReason;
}
