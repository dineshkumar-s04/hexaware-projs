package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.ClaimDTO;
import com.hexaware.careassist.service.IClaimService;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

	@Autowired
	private IClaimService claimService;

	@PostMapping
	public ResponseEntity<ClaimDTO> submitClaim(@RequestBody ClaimDTO claimDTO) {

		ClaimDTO savedClaim = claimService.submitClaim(claimDTO);

		return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
	}

	@GetMapping("/{claimId}")
	public ResponseEntity<ClaimDTO> getClaimById(@PathVariable Integer claimId) {

		ClaimDTO claim = claimService.getClaimById(claimId);

		return ResponseEntity.ok(claim);
	}

	@GetMapping
	public ResponseEntity<List<ClaimDTO>> getAllClaims() {

		List<ClaimDTO> claims = claimService.getAllClaims();

		return ResponseEntity.ok(claims);
	}
	
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<List<ClaimDTO>> getClaimsByPatientId(
	        @PathVariable Integer patientId) {

	    List<ClaimDTO> claims = claimService.getClaimsByPatientId(patientId);

	    return ResponseEntity.ok(claims);
	}

	@PutMapping("/approve/{claimId}")
	public ResponseEntity<ClaimDTO> approveClaim(@PathVariable Integer claimId) {

		ClaimDTO approvedClaim = claimService.approveClaim(claimId);

		return ResponseEntity.ok(approvedClaim);
	}

	@PutMapping("/reject/{claimId}")
	public ResponseEntity<ClaimDTO> rejectClaim(@PathVariable Integer claimId, @RequestParam String reason) {

		ClaimDTO rejectedClaim = claimService.rejectClaim(claimId, reason);

		return ResponseEntity.ok(rejectedClaim);
	}
	
	@GetMapping("/pending")
	public ResponseEntity<List<ClaimDTO>> getPendingClaims() {
	    return ResponseEntity.ok(claimService.getPendingClaims());
	}

	@GetMapping("/processed")
	public ResponseEntity<List<ClaimDTO>> getProcessedClaims() {
	    return ResponseEntity.ok(claimService.getProcessedClaims());
	}
}