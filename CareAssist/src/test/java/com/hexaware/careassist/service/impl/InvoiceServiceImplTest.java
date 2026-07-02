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

import com.hexaware.careassist.dto.InvoiceDTO;
import com.hexaware.careassist.entity.Invoice;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.entity.Provider;
import com.hexaware.careassist.exception.InvoiceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.exception.ProviderNotFoundException;
import com.hexaware.careassist.repository.InvoiceRepository;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.repository.ProviderRepository;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private ProviderRepository providerRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;
    private InvoiceDTO invoiceDTO;
    private Patient patient;
    private Provider provider;

    @BeforeEach
    void setUp() {

        patient = new Patient();
        patient.setPatientId(1);

        provider = new Provider();
        provider.setProviderId(1);

        invoice = new Invoice();
        invoice.setInvoiceId(1);
        invoice.setInvoiceNumber("INV001");
        invoice.setPatient(patient);
        invoice.setProvider(provider);
        invoice.setConsultationFee(new BigDecimal("500"));
        invoice.setDiagnosticTestFee(new BigDecimal("300"));
        invoice.setScanFee(new BigDecimal("1000"));
        invoice.setMedicineFee(new BigDecimal("200"));
        invoice.setTax(new BigDecimal("180"));
        invoice.setTotalAmount(new BigDecimal("2180"));
        invoice.setStatus("PENDING");
        invoice.setInvoiceDate(LocalDate.of(2026, 1, 1));
        invoice.setDueDate(LocalDate.of(2026, 1, 10));

        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setInvoiceId(1);
        invoiceDTO.setInvoiceNumber("INV001");
        invoiceDTO.setPatientId(1);
        invoiceDTO.setProviderId(1);
        invoiceDTO.setConsultationFee(new BigDecimal("500"));
        invoiceDTO.setDiagnosticTestFee(new BigDecimal("300"));
        invoiceDTO.setScanFee(new BigDecimal("1000"));
        invoiceDTO.setMedicineFee(new BigDecimal("200"));
        invoiceDTO.setTax(new BigDecimal("180"));
        invoiceDTO.setTotalAmount(new BigDecimal("2180"));
        invoiceDTO.setStatus("PENDING");
        invoiceDTO.setInvoiceDate(LocalDate.of(2026, 1, 1));
        invoiceDTO.setDueDate(LocalDate.of(2026, 1, 10));
    }

    @Test
    void testGenerateInvoice() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(providerRepository.findById(1))
                .thenReturn(Optional.of(provider));

        when(invoiceRepository.save(any(Invoice.class)))
                .thenReturn(invoice);

        // Act
        InvoiceDTO result = invoiceService.generateInvoice(invoiceDTO);

        // Assert
        assertNotNull(result);
        assertEquals(invoice.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoice.getInvoiceNumber(), result.getInvoiceNumber());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(provider.getProviderId(), result.getProviderId());
        assertEquals(invoice.getConsultationFee(), result.getConsultationFee());
        assertEquals(invoice.getDiagnosticTestFee(), result.getDiagnosticTestFee());
        assertEquals(invoice.getScanFee(), result.getScanFee());
        assertEquals(invoice.getMedicineFee(), result.getMedicineFee());
        assertEquals(invoice.getTax(), result.getTax());
        assertEquals(invoice.getTotalAmount(), result.getTotalAmount());
        assertEquals(invoice.getStatus(), result.getStatus());
        assertEquals(invoice.getInvoiceDate(), result.getInvoiceDate());
        assertEquals(invoice.getDueDate(), result.getDueDate());

        verify(patientRepository).findById(1);
        verify(providerRepository).findById(1);
        verify(invoiceRepository).save(any(Invoice.class));
    }
    
    @Test
    void testGenerateInvoice_PatientNotFound() {

        // Arrange
        invoiceDTO.setPatientId(100);

        when(patientRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> invoiceService.generateInvoice(invoiceDTO));

        assertEquals("Patient not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(100);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }
    
    @Test
    void testGenerateInvoice_ProviderNotFound() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        invoiceDTO.setProviderId(100);

        when(providerRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        ProviderNotFoundException exception = assertThrows(
                ProviderNotFoundException.class,
                () -> invoiceService.generateInvoice(invoiceDTO));

        assertEquals("Provider not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(1);
        verify(providerRepository).findById(100);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testGetInvoiceById() {

        // Arrange
        when(invoiceRepository.findById(1))
                .thenReturn(Optional.of(invoice));

        // Act
        InvoiceDTO result = invoiceService.getInvoiceById(1);

        // Assert
        assertNotNull(result);
        assertEquals(invoice.getInvoiceId(), result.getInvoiceId());
        assertEquals(invoice.getInvoiceNumber(), result.getInvoiceNumber());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(provider.getProviderId(), result.getProviderId());
        assertEquals(invoice.getConsultationFee(), result.getConsultationFee());
        assertEquals(invoice.getDiagnosticTestFee(), result.getDiagnosticTestFee());
        assertEquals(invoice.getScanFee(), result.getScanFee());
        assertEquals(invoice.getMedicineFee(), result.getMedicineFee());
        assertEquals(invoice.getTax(), result.getTax());
        assertEquals(invoice.getTotalAmount(), result.getTotalAmount());
        assertEquals(invoice.getStatus(), result.getStatus());
        assertEquals(invoice.getInvoiceDate(), result.getInvoiceDate());
        assertEquals(invoice.getDueDate(), result.getDueDate());

        verify(invoiceRepository).findById(1);
    }
    
    @Test
    void testGetInvoiceById_NotFound() {

        when(invoiceRepository.findById(100))
                .thenReturn(Optional.empty());

        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> invoiceService.getInvoiceById(100));

        assertEquals("Invoice not found with ID: 100",
                exception.getMessage());

        verify(invoiceRepository).findById(100);
    }

    @Test
    void testGetAllInvoices() {

        // Arrange
        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);

        when(invoiceRepository.findAll()).thenReturn(invoices);

        // Act
        List<InvoiceDTO> result = invoiceService.getAllInvoices();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        InvoiceDTO dto = result.get(0);

        assertEquals(invoice.getInvoiceId(), dto.getInvoiceId());
        assertEquals(invoice.getInvoiceNumber(), dto.getInvoiceNumber());
        assertEquals(patient.getPatientId(), dto.getPatientId());
        assertEquals(provider.getProviderId(), dto.getProviderId());
        assertEquals(invoice.getConsultationFee(), dto.getConsultationFee());
        assertEquals(invoice.getDiagnosticTestFee(), dto.getDiagnosticTestFee());
        assertEquals(invoice.getScanFee(), dto.getScanFee());
        assertEquals(invoice.getMedicineFee(), dto.getMedicineFee());
        assertEquals(invoice.getTax(), dto.getTax());
        assertEquals(invoice.getTotalAmount(), dto.getTotalAmount());
        assertEquals(invoice.getStatus(), dto.getStatus());
        assertEquals(invoice.getInvoiceDate(), dto.getInvoiceDate());
        assertEquals(invoice.getDueDate(), dto.getDueDate());

        verify(invoiceRepository).findAll();
    }

    @Test
    void testUpdateInvoiceStatus() {

        // Arrange
        when(invoiceRepository.findById(1))
                .thenReturn(Optional.of(invoice));

        invoice.setStatus("PAID");

        when(invoiceRepository.save(any(Invoice.class)))
                .thenReturn(invoice);

        // Act
        InvoiceDTO result = invoiceService.updateInvoiceStatus(1, "PAID");

        // Assert
        assertNotNull(result);
        assertEquals("PAID", result.getStatus());
        assertEquals(invoice.getInvoiceId(), result.getInvoiceId());

        verify(invoiceRepository).findById(1);
        verify(invoiceRepository).save(any(Invoice.class));
    }

    @Test
    void testDeleteInvoice() {

        when(invoiceRepository.findById(1))
                .thenReturn(Optional.of(invoice));

        invoiceService.deleteInvoice(1);

        verify(invoiceRepository).findById(1);
        verify(invoiceRepository).delete(invoice);
    }
    
    @Test
    void testDeleteInvoice_NotFound() {

        when(invoiceRepository.findById(100))
                .thenReturn(Optional.empty());

        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> invoiceService.deleteInvoice(100));

        assertEquals("Invoice not found with ID: 100",
                exception.getMessage());

        verify(invoiceRepository).findById(100);
        verify(invoiceRepository, never()).delete(any(Invoice.class));
    }

}
