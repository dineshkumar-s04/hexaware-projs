package com.hexaware.patientinsurance.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.patientinsurance.dto.Patient;
import com.hexaware.patientinsurance.dto.PatientInsuranceDTO;
import com.hexaware.patientinsurance.dto.PatientPatientInsuranceVO;
import com.hexaware.patientinsurance.entity.PatientInsurance;
import com.hexaware.patientinsurance.service.IPatientInsuranceService;

@RestController
@RequestMapping("/api/insurance")
public class PatientInsuranceRestController {

	@Autowired
	IPatientInsuranceService service;
	
	@PostMapping(value="/add", produces = "application/json",consumes = "application/json")
	public PatientInsurance addInsurance(@RequestBody PatientInsuranceDTO patientInsuranceDTO) {

		return service.addInsurance(patientInsuranceDTO);
	}
	
	@GetMapping(value="/get/{insuranceId}" )
	public PatientInsuranceDTO getById(@PathVariable int insuranceId) {

		return service.getById(insuranceId);
	}
	
	@GetMapping("/getall")
	public List<PatientInsurance> getAllInsurances() {

		return service.getAllInsurance();
	}
	
	@GetMapping("/get/product-productInsurance/{insuranceId}")
	public PatientPatientInsuranceVO getPatientAndInsurance(@PathVariable int insuranceId) {
		return service.getPatientAndInsurance(insuranceId);
	}
	
	@PutMapping("/update/patient-by-insurance")
	public String   updatePatientByInsurance(@RequestBody Patient patient) {
		
		
			 service.updatePatientByInsurance(patient);
	
			 return "insurance updated by patient..";
	
	}
	
	
}
