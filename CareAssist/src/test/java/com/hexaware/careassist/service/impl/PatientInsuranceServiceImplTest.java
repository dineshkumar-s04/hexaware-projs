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

import com.hexaware.careassist.dto.PatientInsuranceDTO;
import com.hexaware.careassist.entity.InsurancePlan;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.entity.PatientInsurance;
import com.hexaware.careassist.exception.InsurancePlanNotFoundException;
import com.hexaware.careassist.exception.PatientInsuranceNotFoundException;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.repository.InsurancePlanRepository;
import com.hexaware.careassist.repository.PatientInsuranceRepository;
import com.hexaware.careassist.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
class PatientInsuranceServiceImplTest {

    @Mock
    private PatientInsuranceRepository patientInsuranceRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private InsurancePlanRepository insurancePlanRepository;

    @InjectMocks
    private PatientInsuranceServiceImpl patientInsuranceService;

    private Patient patient;
    private InsurancePlan plan;
    private PatientInsurance enrollment;
    private PatientInsuranceDTO dto;

    @BeforeEach
    void setUp() {

        patient = new Patient();
        patient.setPatientId(1);

        plan = new InsurancePlan();
        plan.setPlanId(1);

        enrollment = new PatientInsurance();
        enrollment.setEnrollmentId(1);
        enrollment.setPatient(patient);
        enrollment.setPlan(plan);
        enrollment.setEnrollmentDate(LocalDate.of(2026,1,1));
        enrollment.setExpiryDate(LocalDate.of(2027,1,1));
        enrollment.setCoverageUsed(new BigDecimal("1000"));
        enrollment.setStatus("ACTIVE");

        dto = new PatientInsuranceDTO();
        dto.setEnrollmentId(1);
        dto.setPatientId(1);
        dto.setPlanId(1);
        dto.setEnrollmentDate(LocalDate.of(2026,1,1));
        dto.setExpiryDate(LocalDate.of(2027,1,1));
        dto.setCoverageUsed(new BigDecimal("1000"));
        dto.setStatus("ACTIVE");
    }

    @Test
    void testEnrollPlan() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(insurancePlanRepository.findById(1))
                .thenReturn(Optional.of(plan));

        when(patientInsuranceRepository.save(any(PatientInsurance.class)))
                .thenReturn(enrollment);

        // Act
        PatientInsuranceDTO result = patientInsuranceService.enrollPlan(dto);

        // Assert
        assertNotNull(result);
        assertEquals(enrollment.getEnrollmentId(), result.getEnrollmentId());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(plan.getPlanId(), result.getPlanId());
        assertEquals(enrollment.getEnrollmentDate(), result.getEnrollmentDate());
        assertEquals(enrollment.getExpiryDate(), result.getExpiryDate());
        assertEquals(enrollment.getCoverageUsed(), result.getCoverageUsed());
        assertEquals(enrollment.getStatus(), result.getStatus());

        verify(patientRepository).findById(1);
        verify(insurancePlanRepository).findById(1);
        verify(patientInsuranceRepository).save(any(PatientInsurance.class));
    }
    
    @Test
    void testEnrollPlan_PatientNotFound() {

        // Arrange
        dto.setPatientId(100);

        when(patientRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> patientInsuranceService.enrollPlan(dto));

        assertEquals("Patient not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(100);
        verify(patientInsuranceRepository, never())
                .save(any(PatientInsurance.class));
    }
    
    @Test
    void testEnrollPlan_PlanNotFound() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        dto.setPlanId(100);

        when(insurancePlanRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        InsurancePlanNotFoundException exception = assertThrows(
                InsurancePlanNotFoundException.class,
                () -> patientInsuranceService.enrollPlan(dto));

        assertEquals("Insurance Plan not found with ID: 100",
                exception.getMessage());

        verify(patientRepository).findById(1);
        verify(insurancePlanRepository).findById(100);
        verify(patientInsuranceRepository, never())
                .save(any(PatientInsurance.class));
    }

    @Test
    void testGetEnrollmentById() {

        // Arrange
        when(patientInsuranceRepository.findById(1))
                .thenReturn(Optional.of(enrollment));

        // Act
        PatientInsuranceDTO result =
                patientInsuranceService.getEnrollmentById(1);

        // Assert
        assertNotNull(result);
        assertEquals(enrollment.getEnrollmentId(), result.getEnrollmentId());
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(plan.getPlanId(), result.getPlanId());
        assertEquals(enrollment.getEnrollmentDate(), result.getEnrollmentDate());
        assertEquals(enrollment.getExpiryDate(), result.getExpiryDate());
        assertEquals(enrollment.getCoverageUsed(), result.getCoverageUsed());
        assertEquals(enrollment.getStatus(), result.getStatus());

        verify(patientInsuranceRepository).findById(1);
    }
    
    @Test
    void testGetEnrollmentById_NotFound() {

        // Arrange
        when(patientInsuranceRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        PatientInsuranceNotFoundException exception = assertThrows(
                PatientInsuranceNotFoundException.class,
                () -> patientInsuranceService.getEnrollmentById(100));

        assertEquals("Enrollment not found with ID: 100",
                exception.getMessage());

        verify(patientInsuranceRepository).findById(100);
    }

    @Test
    void testGetAllEnrollments() {

        // Arrange
        List<PatientInsurance> enrollments = new ArrayList<>();
        enrollments.add(enrollment);

        when(patientInsuranceRepository.findAll())
                .thenReturn(enrollments);

        // Act
        List<PatientInsuranceDTO> result =
                patientInsuranceService.getAllEnrollments();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        PatientInsuranceDTO dtoResult = result.get(0);

        assertEquals(enrollment.getEnrollmentId(), dtoResult.getEnrollmentId());
        assertEquals(patient.getPatientId(), dtoResult.getPatientId());
        assertEquals(plan.getPlanId(), dtoResult.getPlanId());
        assertEquals(enrollment.getEnrollmentDate(), dtoResult.getEnrollmentDate());
        assertEquals(enrollment.getExpiryDate(), dtoResult.getExpiryDate());
        assertEquals(enrollment.getCoverageUsed(), dtoResult.getCoverageUsed());
        assertEquals(enrollment.getStatus(), dtoResult.getStatus());

        verify(patientInsuranceRepository).findAll();
    }

    @Test
    void testCancelEnrollment() {

        // Arrange
        when(patientInsuranceRepository.findById(1))
                .thenReturn(Optional.of(enrollment));

        // Act
        patientInsuranceService.cancelEnrollment(1);

        // Assert
        verify(patientInsuranceRepository).findById(1);
        verify(patientInsuranceRepository).delete(enrollment);
    }
    
    @Test
    void testCancelEnrollment_NotFound() {

        // Arrange
        when(patientInsuranceRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        PatientInsuranceNotFoundException exception = assertThrows(
                PatientInsuranceNotFoundException.class,
                () -> patientInsuranceService.cancelEnrollment(100));

        assertEquals("Enrollment not found with ID: 100",
                exception.getMessage());

        verify(patientInsuranceRepository).findById(100);
        verify(patientInsuranceRepository, never())
                .delete(any(PatientInsurance.class));
    }

}
