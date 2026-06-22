package com.hexaware.springrest.datajpa.service;

import java.util.List;

import com.hexaware.springrest.datajpa.entity.PatientInsurance;

public interface IPatientInsuranceService {

    public PatientInsurance addInsurance(PatientInsurance insurance);

    public PatientInsurance updateInsurance(PatientInsurance insurance);

    public PatientInsurance getByInsuranceId(int insuranceId);

    public void deleteByInsuranceId(int insuranceId);

    public List<PatientInsurance> getAllInsurance();

    public List<PatientInsurance> findByInsuranceCompany(String insuranceCompany);

    public List<PatientInsurance> findByPolicyNumber(String policyNumber);

    public List<PatientInsurance> findByCoverageAmountGreaterThan(double coverageAmount);

}