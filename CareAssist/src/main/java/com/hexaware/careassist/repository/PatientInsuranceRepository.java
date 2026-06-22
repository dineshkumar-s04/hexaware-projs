package com.hexaware.careassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.PatientInsurance;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Integer> {

}