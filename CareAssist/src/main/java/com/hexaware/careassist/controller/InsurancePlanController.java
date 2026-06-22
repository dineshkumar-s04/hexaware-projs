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

import com.hexaware.careassist.dto.InsurancePlanDTO;
import com.hexaware.careassist.service.IInsurancePlanService;

@RestController
@RequestMapping("/api/plans")
public class InsurancePlanController {

	@Autowired
	private IInsurancePlanService insurancePlanService;

	@PostMapping
	public ResponseEntity<InsurancePlanDTO> addPlan(@RequestBody InsurancePlanDTO planDTO) {

		InsurancePlanDTO savedPlan = insurancePlanService.addPlan(planDTO);

		return new ResponseEntity<>(savedPlan, HttpStatus.CREATED);
	}

	@GetMapping("/{planId}")
	public ResponseEntity<InsurancePlanDTO> getPlanById(@PathVariable Integer planId) {

		InsurancePlanDTO plan = insurancePlanService.getPlanById(planId);

		return ResponseEntity.ok(plan);
	}

	@GetMapping
	public ResponseEntity<List<InsurancePlanDTO>> getAllPlans() {

		List<InsurancePlanDTO> plans = insurancePlanService.getAllPlans();

		return ResponseEntity.ok(plans);
	}

	@PutMapping("/{planId}")
	public ResponseEntity<InsurancePlanDTO> updatePlan(@PathVariable Integer planId,
			@RequestBody InsurancePlanDTO planDTO) {

		InsurancePlanDTO updatedPlan = insurancePlanService.updatePlan(planId, planDTO);

		return ResponseEntity.ok(updatedPlan);
	}

	@DeleteMapping("/{planId}")
	public ResponseEntity<String> deletePlan(@PathVariable Integer planId) {

		insurancePlanService.deletePlan(planId);

		return ResponseEntity.ok("Insurance Plan deleted successfully");
	}
}