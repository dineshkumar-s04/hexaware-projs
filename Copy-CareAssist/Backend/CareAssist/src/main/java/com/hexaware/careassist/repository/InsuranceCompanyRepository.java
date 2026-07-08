package com.hexaware.careassist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.careassist.entity.InsuranceCompany;

@Repository
public interface InsuranceCompanyRepository extends JpaRepository<InsuranceCompany, Integer> {

}