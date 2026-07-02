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

import com.hexaware.careassist.dto.ClaimDTO;
import com.hexaware.careassist.dto.ClaimDocumentDTO;
import com.hexaware.careassist.entity.Claim;
import com.hexaware.careassist.entity.ClaimDocument;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.Invoice;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.exception.ClaimNotFoundException;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.InvoiceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.repository.ClaimDocumentRepository;
import com.hexaware.careassist.repository.ClaimRepository;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.InvoiceRepository;
import com.hexaware.careassist.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
class ClaimServiceImplTest {

    @Mock
    private ClaimRepository claimRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private InsuranceCompanyRepository insuranceCompanyRepository;

    @Mock
    private ClaimDocumentRepository claimDocumentRepository;

    @InjectMocks
    private ClaimServiceImpl claimService;

    private Claim claim;
    private ClaimDTO claimDTO;
    private Patient patient;
    private Invoice invoice;
    private InsuranceCompany company;
    private ClaimDocument document;
    private ClaimDocumentDTO documentDTO;

    @BeforeEach
    void setUp() {

        patient = new Patient();
        patient.setPatientId(1);

        invoice = new Invoice();
        invoice.setInvoiceId(1);

        company = new InsuranceCompany();
        company.setCompanyId(1);

        claim = new Claim();
        claim.setClaimId(1);
        claim.setPatient(patient);
        claim.setInvoice(invoice);
        claim.setCompany(company);
        claim.setClaimAmount(new BigDecimal("5000"));
        claim.setDiagnosis("Viral Fever");
        claim.setTreatmentDetails("Blood Test");
        claim.setClaimDate(LocalDate.of(2026, 1, 1));
        claim.setStatus("PENDING");

        document = new ClaimDocument();
        document.setDocumentId(1);
        document.setClaim(claim);
        document.setFileName("report.pdf");
        document.setFilePath("/documents/report.pdf");

        documentDTO = new ClaimDocumentDTO();
        documentDTO.setDocumentId(1);
        documentDTO.setFileName("report.pdf");
        documentDTO.setFilePath("/documents/report.pdf");

        List<ClaimDocumentDTO> documents = new ArrayList<>();
        documents.add(documentDTO);

        claimDTO = new ClaimDTO();
        claimDTO.setClaimId(1);
        claimDTO.setPatientId(1);
        claimDTO.setInvoiceId(1);
        claimDTO.setCompanyId(1);
        claimDTO.setClaimAmount(new BigDecimal("5000"));
        claimDTO.setDiagnosis("Viral Fever");
        claimDTO.setTreatmentDetails("Blood Test");
        claimDTO.setClaimDate(LocalDate.of(2026, 1, 1));
        claimDTO.setStatus("PENDING");
        claimDTO.setDocuments(documents);
    }

    @Test
    void testSubmitClaim() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(invoiceRepository.findById(1))
                .thenReturn(Optional.of(invoice));

        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        when(claimRepository.save(any(Claim.class)))
                .thenReturn(claim);

        when(claimDocumentRepository.save(any(ClaimDocument.class)))
                .thenReturn(document);

        // Act
        ClaimDTO result = claimService.submitClaim(claimDTO);

        // Assert
        assertNotNull(result);
        assertEquals(claim.getClaimId(), result.getClaimId());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(invoice.getInvoiceId(), result.getInvoiceId());
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(claim.getClaimAmount(), result.getClaimAmount());
        assertEquals(claim.getDiagnosis(), result.getDiagnosis());
        assertEquals(claim.getTreatmentDetails(), result.getTreatmentDetails());
        assertEquals(claim.getClaimDate(), result.getClaimDate());
        assertEquals(claim.getStatus(), result.getStatus());

        assertNotNull(result.getDocuments());
        assertEquals(1, result.getDocuments().size());

        verify(patientRepository).findById(1);
        verify(invoiceRepository).findById(1);
        verify(insuranceCompanyRepository).findById(1);
        verify(claimRepository).save(any(Claim.class));
        verify(claimDocumentRepository).save(any(ClaimDocument.class));
    }
    
    @Test
    void testSubmitClaim_PatientNotFound() {

        // Arrange
        claimDTO.setPatientId(100);

        when(patientRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> claimService.submitClaim(claimDTO));

        assertEquals("Patient not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(100);
        verify(claimRepository, never()).save(any(Claim.class));
    }
    
    @Test
    void testSubmitClaim_InvoiceNotFound() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        claimDTO.setInvoiceId(100);

        when(invoiceRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        InvoiceNotFoundException exception = assertThrows(
                InvoiceNotFoundException.class,
                () -> claimService.submitClaim(claimDTO));

        assertEquals("Invoice not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(1);
        verify(invoiceRepository).findById(100);
        verify(claimRepository, never()).save(any(Claim.class));
    }
    
    @Test
    void testSubmitClaim_CompanyNotFound() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(invoiceRepository.findById(1))
                .thenReturn(Optional.of(invoice));

        claimDTO.setCompanyId(100);

        when(insuranceCompanyRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        InsuranceCompanyNotFoundException exception = assertThrows(
                InsuranceCompanyNotFoundException.class,
                () -> claimService.submitClaim(claimDTO));

        assertEquals("Insurance Company not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(1);
        verify(invoiceRepository).findById(1);
        verify(insuranceCompanyRepository).findById(100);
        verify(claimRepository, never()).save(any(Claim.class));
    }

    @Test
    void testGetClaimById() {

        // Arrange
        List<ClaimDocument> documents = new ArrayList<>();
        documents.add(document);

        when(claimRepository.findById(1))
                .thenReturn(Optional.of(claim));

        when(claimDocumentRepository.findByClaimClaimId(1))
                .thenReturn(documents);

        // Act
        ClaimDTO result = claimService.getClaimById(1);

        // Assert
        assertNotNull(result);
        assertEquals(claim.getClaimId(), result.getClaimId());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(invoice.getInvoiceId(), result.getInvoiceId());
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(claim.getClaimAmount(), result.getClaimAmount());
        assertEquals(claim.getDiagnosis(), result.getDiagnosis());
        assertEquals(claim.getTreatmentDetails(), result.getTreatmentDetails());
        assertEquals(claim.getClaimDate(), result.getClaimDate());
        assertEquals(claim.getStatus(), result.getStatus());

        assertNotNull(result.getDocuments());
        assertEquals(1, result.getDocuments().size());

        verify(claimRepository).findById(1);
        verify(claimDocumentRepository).findByClaimClaimId(1);
    }
    
    @Test
    void testGetClaimById_NotFound() {

        when(claimRepository.findById(100))
                .thenReturn(Optional.empty());

        ClaimNotFoundException exception = assertThrows(
                ClaimNotFoundException.class,
                () -> claimService.getClaimById(100));

        assertEquals("Claim not found with ID: 100",
                exception.getMessage());

        verify(claimRepository).findById(100);
    }

    @Test
    void testGetAllClaims() {

        // Arrange
        List<Claim> claims = new ArrayList<>();
        claims.add(claim);

        List<ClaimDocument> documents = new ArrayList<>();
        documents.add(document);

        when(claimRepository.findAll())
                .thenReturn(claims);

        when(claimDocumentRepository.findByClaimClaimId(1))
                .thenReturn(documents);

        // Act
        List<ClaimDTO> result = claimService.getAllClaims();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        ClaimDTO dto = result.get(0);

        assertEquals(claim.getClaimId(), dto.getClaimId());
        assertEquals(patient.getPatientId(), dto.getPatientId());
        assertEquals(invoice.getInvoiceId(), dto.getInvoiceId());
        assertEquals(company.getCompanyId(), dto.getCompanyId());
        assertEquals(claim.getClaimAmount(), dto.getClaimAmount());
        assertEquals(claim.getDiagnosis(), dto.getDiagnosis());
        assertEquals(claim.getTreatmentDetails(), dto.getTreatmentDetails());
        assertEquals(claim.getClaimDate(), dto.getClaimDate());
        assertEquals(claim.getStatus(), dto.getStatus());

        assertNotNull(dto.getDocuments());
        assertEquals(1, dto.getDocuments().size());

        verify(claimRepository).findAll();
        verify(claimDocumentRepository).findByClaimClaimId(1);
    }

    @Test
    void testApproveClaim() {

        // Arrange
        List<ClaimDocument> documents = new ArrayList<>();
        documents.add(document);

        when(claimRepository.findById(1))
                .thenReturn(Optional.of(claim));

        claim.setStatus("APPROVED");

        when(claimRepository.save(any(Claim.class)))
                .thenReturn(claim);

        when(claimDocumentRepository.findByClaimClaimId(1))
                .thenReturn(documents);

        // Act
        ClaimDTO result = claimService.approveClaim(1);

        // Assert
        assertNotNull(result);
        assertEquals("APPROVED", result.getStatus());
        assertEquals(claim.getClaimId(), result.getClaimId());

        assertNotNull(result.getDocuments());
        assertEquals(1, result.getDocuments().size());

        verify(claimRepository).findById(1);
        verify(claimRepository).save(any(Claim.class));
        verify(claimDocumentRepository).findByClaimClaimId(1);
    }

    @Test
    void testRejectClaim() {

        // Arrange
        List<ClaimDocument> documents = new ArrayList<>();
        documents.add(document);

        when(claimRepository.findById(1))
                .thenReturn(Optional.of(claim));

        claim.setStatus("REJECTED");
        claim.setRejectionReason("Invalid Documents");

        when(claimRepository.save(any(Claim.class)))
                .thenReturn(claim);

        when(claimDocumentRepository.findByClaimClaimId(1))
                .thenReturn(documents);

        // Act
        ClaimDTO result =
                claimService.rejectClaim(1, "Invalid Documents");

        // Assert
        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
        assertEquals("Invalid Documents", result.getRejectionReason());

        assertNotNull(result.getDocuments());
        assertEquals(1, result.getDocuments().size());

        verify(claimRepository).findById(1);
        verify(claimRepository).save(any(Claim.class));
        verify(claimDocumentRepository).findByClaimClaimId(1);
    }
    
    @Test
    void testApproveClaim_NotFound() {

        when(claimRepository.findById(100))
                .thenReturn(Optional.empty());

        ClaimNotFoundException exception = assertThrows(
                ClaimNotFoundException.class,
                () -> claimService.approveClaim(100));

        assertEquals("Claim not found with ID: 100",
                exception.getMessage());

        verify(claimRepository).findById(100);
        verify(claimRepository, never()).save(any(Claim.class));
    }
    
    @Test
    void testRejectClaim_NotFound() {

        when(claimRepository.findById(100))
                .thenReturn(Optional.empty());

        ClaimNotFoundException exception = assertThrows(
                ClaimNotFoundException.class,
                () -> claimService.rejectClaim(100, "Reason"));

        assertEquals("Claim not found with ID: 100",
                exception.getMessage());

        verify(claimRepository).findById(100);
        verify(claimRepository, never()).save(any(Claim.class));
    }

}
