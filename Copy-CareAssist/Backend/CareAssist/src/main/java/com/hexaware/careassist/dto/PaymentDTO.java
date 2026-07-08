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
public class PaymentDTO {

	
	private int paymentId;
	private int claimId;

	private BigDecimal amount;

	private LocalDate paymentDate;

	private String status;
	private String transactionRef;
}
