package com.hexaware.springrest.datajpa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.springrest.datajpa.entity.PatientInsurance;
import com.hexaware.springrest.datajpa.service.IPatientInsuranceService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/insurance")
@Slf4j
@AllArgsConstructor
public class PatientInsuranceRestController {

    IPatientInsuranceService service;

    static {
        log.info("Patient Insurance Controller Initialized");
    }

    @PostMapping("/add")
    public PatientInsurance addInsurance(@RequestBody PatientInsurance insurance) {
        return service.addInsurance(insurance);
    }

    @PutMapping("/update")
    public PatientInsurance updateInsurance(@RequestBody PatientInsurance insurance) {
        return service.updateInsurance(insurance);
    }

    @GetMapping("/getbyid/{insuranceId}")
    public PatientInsurance getByInsuranceId(@PathVariable int insuranceId) {
        return service.getByInsuranceId(insuranceId);
    }

    @GetMapping("/getall")
    public List<PatientInsurance> getAllInsurance() {
        return service.getAllInsurance();
    }

    @DeleteMapping("/delete/{insuranceId}")
    public ResponseEntity<String> deleteInsurance(@PathVariable int insuranceId) {

        service.deleteByInsuranceId(insuranceId);

        return new ResponseEntity<>("Insurance Record Deleted Successfully",
                HttpStatus.ACCEPTED);
    }

    @GetMapping("/company/{insuranceCompany}")
    public List<PatientInsurance> findByInsuranceCompany(
            @PathVariable String insuranceCompany) {

        return service.findByInsuranceCompany(insuranceCompany);
    }

    @GetMapping("/policy/{policyNumber}")
    public List<PatientInsurance> findByPolicyNumber(
            @PathVariable String policyNumber) {

        return service.findByPolicyNumber(policyNumber);
    }

    @GetMapping("/coverage/{coverageAmount}")
    public List<PatientInsurance> findByCoverageAmountGreaterThan(
            @PathVariable double coverageAmount) {

        return service.findByCoverageAmountGreaterThan(coverageAmount);
    }
}