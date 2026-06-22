package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.PaymentDTO;

public interface IPaymentService {

    PaymentDTO processPayment(
            PaymentDTO paymentDTO);

    PaymentDTO getPaymentById(
            Integer paymentId);

    List<PaymentDTO> getAllPayments();
}