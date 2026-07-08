package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.PaymentDTO;
import com.hexaware.careassist.service.IPaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private IPaymentService paymentService;

	@PostMapping
	public ResponseEntity<PaymentDTO> processPayment(@RequestBody PaymentDTO paymentDTO) {

		PaymentDTO savedPayment = paymentService.processPayment(paymentDTO);

		return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
	}

	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Integer paymentId) {

		PaymentDTO payment = paymentService.getPaymentById(paymentId);

		return ResponseEntity.ok(payment);
	}

	@GetMapping
	public ResponseEntity<List<PaymentDTO>> getAllPayments() {

		List<PaymentDTO> payments = paymentService.getAllPayments();

		return ResponseEntity.ok(payments);
	}
}