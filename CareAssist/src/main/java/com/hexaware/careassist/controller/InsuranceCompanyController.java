package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.InsuranceCompanyDTO;
import com.hexaware.careassist.service.IInsuranceCompanyService;

@RestController
@RequestMapping("/api/companies")
public class InsuranceCompanyController {

	@Autowired
	private IInsuranceCompanyService insuranceCompanyService;

	@PostMapping
	public ResponseEntity<InsuranceCompanyDTO> addCompany(@RequestBody InsuranceCompanyDTO companyDTO) {

		InsuranceCompanyDTO savedCompany = insuranceCompanyService.addCompany(companyDTO);

		return new ResponseEntity<>(savedCompany, HttpStatus.CREATED);
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<InsuranceCompanyDTO> getCompanyById(@PathVariable Integer companyId) {

		InsuranceCompanyDTO company = insuranceCompanyService.getCompanyById(companyId);

		return ResponseEntity.ok(company);
	}

	@GetMapping
	public ResponseEntity<List<InsuranceCompanyDTO>> getAllCompanies() {

		List<InsuranceCompanyDTO> companies = insuranceCompanyService.getAllCompanies();

		return ResponseEntity.ok(companies);
	}

	@PutMapping("/{companyId}")
	public ResponseEntity<InsuranceCompanyDTO> updateCompany(@PathVariable Integer companyId,
			@RequestBody InsuranceCompanyDTO companyDTO) {

		InsuranceCompanyDTO updatedCompany = insuranceCompanyService.updateCompany(companyId, companyDTO);

		return ResponseEntity.ok(updatedCompany);
	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity<String> deleteCompany(@PathVariable Integer companyId) {

		insuranceCompanyService.deleteCompany(companyId);

		return ResponseEntity.ok("Insurance Company deleted successfully");
	}
}