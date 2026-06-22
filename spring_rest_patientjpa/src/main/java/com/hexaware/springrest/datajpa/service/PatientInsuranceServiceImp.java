package com.hexaware.springrest.datajpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.springrest.datajpa.entity.PatientInsurance;
import com.hexaware.springrest.datajpa.repository.PatientInsuranceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PatientInsuranceServiceImp implements IPatientInsuranceService {

    @Autowired
    PatientInsuranceRepository repo;

    @Override
    public PatientInsurance addInsurance(PatientInsurance insurance) {

        log.info("Adding Patient Insurance");

        return repo.save(insurance);
    }

    @Override
    public PatientInsurance updateInsurance(PatientInsurance insurance) {

        log.info("Updating Patient Insurance");

        return repo.save(insurance);
    }

    @Override
    public PatientInsurance getByInsuranceId(int insuranceId) {

        return repo.findById(insuranceId).orElse(null);
    }

    @Override
    public void deleteByInsuranceId(int insuranceId) {

        repo.deleteById(insuranceId);
    }

    @Override
    public List<PatientInsurance> getAllInsurance() {

        log.info("Displaying all Patient Insurance");

        return repo.findAll();
    }

    @Override
    public List<PatientInsurance> findByInsuranceCompany(String insuranceCompany) {

        return repo.findByInsuranceCompany(insuranceCompany);
    }

    @Override
    public List<PatientInsurance> findByPolicyNumber(String policyNumber) {

        return repo.findByPolicyNumber(policyNumber);
    }

    @Override
    public List<PatientInsurance> findByCoverageAmountGreaterThan(double coverageAmount) {

        return repo.findByCoverageAmountGreaterThan(coverageAmount);
    }

}