package com.hexaware.patientinsurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.patientinsurance.entity.PatientInsurance;

public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Integer> {

}
