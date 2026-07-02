package com.hexaware.careassist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexaware.careassist.dto.PaymentDTO;
import com.hexaware.careassist.entity.Claim;
import com.hexaware.careassist.entity.Payment;
import com.hexaware.careassist.exception.ClaimNotFoundException;
import com.hexaware.careassist.exception.PaymentNotFoundException;
import com.hexaware.careassist.repository.ClaimRepository;
import com.hexaware.careassist.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Claim claim;
    private Payment payment;
    private PaymentDTO paymentDTO;

    @BeforeEach
    void setUp() {

        claim = new Claim();
        claim.setClaimId(1);

        payment = new Payment();
        payment.setPaymentId(1);
        payment.setClaim(claim);
        payment.setAmount(new BigDecimal("5000"));
        payment.setPaymentDate(LocalDate.of(2026, 1, 5));
        payment.setStatus("SUCCESS");
        payment.setTransactionRef("TXN123456");

        paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(1);
        paymentDTO.setClaimId(1);
        paymentDTO.setAmount(new BigDecimal("5000"));
        paymentDTO.setPaymentDate(LocalDate.of(2026, 1, 5));
        paymentDTO.setStatus("SUCCESS");
        paymentDTO.setTransactionRef("TXN123456");
    }
    
    @Test
    void testProcessPayment() {

        // Arrange
        when(claimRepository.findById(1))
                .thenReturn(Optional.of(claim));

        when(paymentRepository.save(any(Payment.class)))
                .thenReturn(payment);

        // Act
        PaymentDTO result = paymentService.processPayment(paymentDTO);

        // Assert
        assertNotNull(result);
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertEquals(claim.getClaimId(), result.getClaimId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertEquals(payment.getPaymentDate(), result.getPaymentDate());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getTransactionRef(), result.getTransactionRef());

        verify(claimRepository).findById(1);
        verify(paymentRepository).save(any(Payment.class));
    }
    
    @Test
    void testProcessPayment_ClaimNotFound() {

        // Arrange
        paymentDTO.setClaimId(100);

        when(claimRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        ClaimNotFoundException exception = assertThrows(
                ClaimNotFoundException.class,
                () -> paymentService.processPayment(paymentDTO));

        assertEquals("Claim not found with ID: 100",
                exception.getMessage());

        verify(claimRepository).findById(100);
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testGetPaymentById() {

        // Arrange
        when(paymentRepository.findById(1))
                .thenReturn(Optional.of(payment));

        // Act
        PaymentDTO result = paymentService.getPaymentById(1);

        // Assert
        assertNotNull(result);
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertEquals(claim.getClaimId(), result.getClaimId());
        assertEquals(payment.getAmount(), result.getAmount());
        assertEquals(payment.getPaymentDate(), result.getPaymentDate());
        assertEquals(payment.getStatus(), result.getStatus());
        assertEquals(payment.getTransactionRef(), result.getTransactionRef());

        verify(paymentRepository).findById(1);
    }
    
    @Test
    void testGetPaymentById_NotFound() {

        // Arrange
        when(paymentRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        PaymentNotFoundException exception = assertThrows(
                PaymentNotFoundException.class,
                () -> paymentService.getPaymentById(100));

        assertEquals("Payment not found with ID: 100",
                exception.getMessage());

        verify(paymentRepository).findById(100);
    }
    
    @Test
    void testGetAllPayments() {

        // Arrange
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);

        when(paymentRepository.findAll())
                .thenReturn(payments);

        // Act
        List<PaymentDTO> result = paymentService.getAllPayments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        PaymentDTO dto = result.get(0);

        assertEquals(payment.getPaymentId(), dto.getPaymentId());
        assertEquals(claim.getClaimId(), dto.getClaimId());
        assertEquals(payment.getAmount(), dto.getAmount());
        assertEquals(payment.getPaymentDate(), dto.getPaymentDate());
        assertEquals(payment.getStatus(), dto.getStatus());
        assertEquals(payment.getTransactionRef(), dto.getTransactionRef());

        verify(paymentRepository).findAll();
    }

}
