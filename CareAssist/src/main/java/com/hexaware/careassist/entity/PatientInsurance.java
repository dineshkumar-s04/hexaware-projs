package com.hexaware.careassist.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "patient_insurance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientInsurance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enrollment_id")
	private int enrollmentId;

	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "plan_id")
	private InsurancePlan plan;

	@Column(name = "enrollment_date")
	private LocalDate enrollmentDate;

	@Column(name = "expiry_date")
	private LocalDate expiryDate;

	@Column(name = "coverage_used")
	private BigDecimal coverageUsed;

	private String status;
}