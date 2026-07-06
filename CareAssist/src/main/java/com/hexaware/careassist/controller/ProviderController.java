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

import com.hexaware.careassist.dto.ProviderDTO;
import com.hexaware.careassist.service.IProviderService;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

	@Autowired
	private IProviderService providerService;

	@PostMapping
	public ResponseEntity<ProviderDTO> addProvider(@RequestBody ProviderDTO providerDTO) {

		ProviderDTO savedProvider = providerService.addProvider(providerDTO);

		return new ResponseEntity<>(savedProvider, HttpStatus.CREATED);
	}

	@GetMapping("/{providerId}")
	public ResponseEntity<ProviderDTO> getProviderById(@PathVariable Integer providerId) {

		ProviderDTO provider = providerService.getProviderById(providerId);

		return ResponseEntity.ok(provider);
	}

	@GetMapping
	public ResponseEntity<List<ProviderDTO>> getAllProviders() {

		List<ProviderDTO> providers = providerService.getAllProviders();

		return ResponseEntity.ok(providers);
	}

	@PutMapping("/{providerId}")
	public ResponseEntity<ProviderDTO> updateProvider(@PathVariable Integer providerId,
			@RequestBody ProviderDTO providerDTO) {

		ProviderDTO updatedProvider = providerService.updateProvider(providerId, providerDTO);

		return ResponseEntity.ok(updatedProvider);
	}
	
	@GetMapping("/email/{email}")
	public ResponseEntity<ProviderDTO> getProviderByEmail(
	        @PathVariable String email) {

	    ProviderDTO provider = providerService.getProviderByEmail(email);

	    return ResponseEntity.ok(provider);
	}

	@DeleteMapping("/{providerId}")
	public ResponseEntity<String> deleteProvider(@PathVariable Integer providerId) {

		providerService.deleteProvider(providerId);

		return ResponseEntity.ok("Provider deleted successfully");
	}
}