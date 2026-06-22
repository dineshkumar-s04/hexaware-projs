package com.hexaware.careassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.InsurancePlan;

@Repository
public interface InsurancePlanRepository extends JpaRepository<InsurancePlan, Integer> {

}