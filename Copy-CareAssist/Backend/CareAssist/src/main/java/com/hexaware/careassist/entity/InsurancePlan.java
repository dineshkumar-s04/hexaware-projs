package com.hexaware.careassist.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "insurance_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsurancePlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private int planId;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private InsuranceCompany company;

	@Column(name = "plan_name")
	private String planName;

	@Column(name = "coverage_amount")
	private BigDecimal coverageAmount;

	private BigDecimal premium;

	private String description;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}