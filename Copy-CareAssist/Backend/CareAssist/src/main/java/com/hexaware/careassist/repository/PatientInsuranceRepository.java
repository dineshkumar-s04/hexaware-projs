package com.hexaware.careassist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.PatientInsurance;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Integer> {
	List<PatientInsurance> findByPatientPatientId(Integer patientId);

}