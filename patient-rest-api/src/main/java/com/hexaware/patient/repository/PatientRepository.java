package com.hexaware.patient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.patient.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
