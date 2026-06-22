package com.hexaware.springrest.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.springrest.datajpa.entity.PatientInsurance;

public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Integer> {

    public List<PatientInsurance> findByInsuranceCompany(String insuranceCompany);

    public List<PatientInsurance> findByPolicyNumber(String policyNumber);

    public List<PatientInsurance> findByCoverageAmountGreaterThan(double coverageAmount);

}