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
public class InvoiceDTO {

	
	private int invoiceId;
	private String invoiceNumber;
	private int patientId;
	private int providerId;

	private BigDecimal consultationFee;
	private BigDecimal diagnosticTestFee;
	private BigDecimal scanFee;
	private BigDecimal medicineFee;

	private BigDecimal tax;
	private BigDecimal totalAmount;

	private String status;

	private LocalDate invoiceDate;
	private LocalDate dueDate;
}
