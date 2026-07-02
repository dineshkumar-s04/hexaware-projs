package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.PaymentDTO;
import com.hexaware.careassist.entity.Claim;
import com.hexaware.careassist.entity.Payment;
import com.hexaware.careassist.exception.ClaimNotFoundException;
import com.hexaware.careassist.exception.PaymentNotFoundException;
import com.hexaware.careassist.repository.ClaimRepository;
import com.hexaware.careassist.repository.PaymentRepository;
import com.hexaware.careassist.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {

	private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ClaimRepository claimRepository;

	@Override
	public PaymentDTO processPayment(PaymentDTO paymentDTO) {

		logger.info("Processing payment for claim id {}", paymentDTO.getClaimId());

		Claim claim = claimRepository.findById(paymentDTO.getClaimId()).orElseThrow(() -> {

			logger.warn("Claim not found with id {}", paymentDTO.getClaimId());

			return new ClaimNotFoundException("Claim not found with ID: " + paymentDTO.getClaimId());
		});

		Payment payment = new Payment();

		payment.setClaim(claim);
		payment.setAmount(paymentDTO.getAmount());
		payment.setPaymentDate(paymentDTO.getPaymentDate());
		payment.setStatus(paymentDTO.getStatus());
		payment.setTransactionRef(paymentDTO.getTransactionRef());

		Payment savedPayment = paymentRepository.save(payment);

		logger.info("Payment processed successfully with id {}", savedPayment.getPaymentId());

		PaymentDTO dto = new PaymentDTO();

		dto.setPaymentId(savedPayment.getPaymentId());
		dto.setClaimId(savedPayment.getClaim().getClaimId());
		dto.setAmount(savedPayment.getAmount());
		dto.setPaymentDate(savedPayment.getPaymentDate());
		dto.setStatus(savedPayment.getStatus());
		dto.setTransactionRef(savedPayment.getTransactionRef());

		return dto;
	}

	@Override
	public PaymentDTO getPaymentById(Integer paymentId) {

		logger.info("Fetching payment with id {}", paymentId);

		Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> {

			logger.warn("Payment not found with id {}", paymentId);

			return new PaymentNotFoundException("Payment not found with ID: " + paymentId);
		});

		PaymentDTO dto = new PaymentDTO();

		dto.setPaymentId(payment.getPaymentId());
		dto.setClaimId(payment.getClaim().getClaimId());
		dto.setAmount(payment.getAmount());
		dto.setPaymentDate(payment.getPaymentDate());
		dto.setStatus(payment.getStatus());
		dto.setTransactionRef(payment.getTransactionRef());

		return dto;
	}

	@Override
	public List<PaymentDTO> getAllPayments() {

		logger.info("Fetching all payments");

		List<Payment> payments = paymentRepository.findAll();

		List<PaymentDTO> dtoList = new ArrayList<>();

		for (Payment payment : payments) {

			PaymentDTO dto = new PaymentDTO();

			dto.setPaymentId(payment.getPaymentId());
			dto.setClaimId(payment.getClaim().getClaimId());
			dto.setAmount(payment.getAmount());
			dto.setPaymentDate(payment.getPaymentDate());
			dto.setStatus(payment.getStatus());
			dto.setTransactionRef(payment.getTransactionRef());

			dtoList.add(dto);
		}

		logger.info("Total payments fetched: {}", dtoList.size());

		return dtoList;
	}
}