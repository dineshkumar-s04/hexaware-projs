package com.hexaware.careassist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexaware.careassist.dto.InsurancePlanDTO;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.InsurancePlan;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.InsurancePlanNotFoundException;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.InsurancePlanRepository;

@ExtendWith(MockitoExtension.class)
class InsurancePlanServiceImplTest {

    @Mock
    private InsurancePlanRepository insurancePlanRepository;

    @Mock
    private InsuranceCompanyRepository insuranceCompanyRepository;

    @InjectMocks
    private InsurancePlanServiceImpl insurancePlanService;

    private InsurancePlan plan;
    private InsurancePlanDTO planDTO;
    private InsuranceCompany company;

    @BeforeEach
    void setUp() {

        company = new InsuranceCompany();
        company.setCompanyId(1);
        company.setCompanyName("Star Health");

        plan = new InsurancePlan();
        plan.setPlanId(1);
        plan.setCompany(company);
        plan.setPlanName("Gold Plan");
        plan.setCoverageAmount(new BigDecimal("500000"));
        plan.setPremium(new BigDecimal("1500"));
        plan.setDescription("Premium Health Insurance");

        planDTO = new InsurancePlanDTO();
        planDTO.setPlanId(1);
        planDTO.setCompanyId(1);
        planDTO.setPlanName("Gold Plan");
        planDTO.setCoverageAmount(new BigDecimal("500000"));
        planDTO.setPremium(new BigDecimal("1500"));
        planDTO.setDescription("Premium Health Insurance");
    }

    @Test
    void testAddPlan() {

        // Arrange
        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        when(insurancePlanRepository.save(any(InsurancePlan.class)))
                .thenReturn(plan);

        // Act
        InsurancePlanDTO result = insurancePlanService.addPlan(planDTO);

        // Assert
        assertNotNull(result);
        assertEquals(plan.getPlanId(), result.getPlanId());
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(plan.getPlanName(), result.getPlanName());
        assertEquals(plan.getCoverageAmount(), result.getCoverageAmount());
        assertEquals(plan.getPremium(), result.getPremium());
        assertEquals(plan.getDescription(), result.getDescription());

        verify(insuranceCompanyRepository, times(1)).findById(1);
        verify(insurancePlanRepository, times(1)).save(any(InsurancePlan.class));
    }
    
    @Test
    void testAddPlan_CompanyNotFound() {

        // Arrange
        planDTO.setCompanyId(100);

        when(insuranceCompanyRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        InsuranceCompanyNotFoundException exception = assertThrows(
                InsuranceCompanyNotFoundException.class,
                () -> insurancePlanService.addPlan(planDTO));

        assertEquals("Insurance Company not found with ID: 100",
                exception.getMessage());

        verify(insuranceCompanyRepository).findById(100);
        verify(insurancePlanRepository, never()).save(any(InsurancePlan.class));
    }

    @Test
    void testGetPlanById() {

        // Arrange
        when(insurancePlanRepository.findById(1))
                .thenReturn(Optional.of(plan));

        // Act
        InsurancePlanDTO result = insurancePlanService.getPlanById(1);

        // Assert
        assertNotNull(result);
        assertEquals(plan.getPlanId(), result.getPlanId());
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(plan.getPlanName(), result.getPlanName());
        assertEquals(plan.getCoverageAmount(), result.getCoverageAmount());
        assertEquals(plan.getPremium(), result.getPremium());
        assertEquals(plan.getDescription(), result.getDescription());

        verify(insurancePlanRepository).findById(1);
    }
    
    @Test
    void testGetPlanById_PlanNotFound() {

        // Arrange
        when(insurancePlanRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        InsurancePlanNotFoundException exception = assertThrows(
                InsurancePlanNotFoundException.class,
                () -> insurancePlanService.getPlanById(100));

        assertEquals("Insurance Plan not found with ID: 100",
                exception.getMessage());

        verify(insurancePlanRepository).findById(100);
    }

    @Test
    void testGetAllPlans() {

        // Arrange
        List<InsurancePlan> plans = new ArrayList<>();
        plans.add(plan);

        when(insurancePlanRepository.findAll()).thenReturn(plans);

        // Act
        List<InsurancePlanDTO> result = insurancePlanService.getAllPlans();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        InsurancePlanDTO dto = result.get(0);

        assertEquals(plan.getPlanId(), dto.getPlanId());
        assertEquals(company.getCompanyId(), dto.getCompanyId());
        assertEquals(plan.getPlanName(), dto.getPlanName());
        assertEquals(plan.getCoverageAmount(), dto.getCoverageAmount());
        assertEquals(plan.getPremium(), dto.getPremium());
        assertEquals(plan.getDescription(), dto.getDescription());

        verify(insurancePlanRepository).findAll();
    }

    @Test
    void testUpdatePlan() {

        // Arrange
        when(insurancePlanRepository.findById(1))
                .thenReturn(Optional.of(plan));

        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        when(insurancePlanRepository.save(any(InsurancePlan.class)))
                .thenReturn(plan);

        // Act
        InsurancePlanDTO result =
                insurancePlanService.updatePlan(1, planDTO);

        // Assert
        assertNotNull(result);
        assertEquals(plan.getPlanId(), result.getPlanId());
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(plan.getPlanName(), result.getPlanName());
        assertEquals(plan.getCoverageAmount(), result.getCoverageAmount());
        assertEquals(plan.getPremium(), result.getPremium());
        assertEquals(plan.getDescription(), result.getDescription());

        verify(insurancePlanRepository).findById(1);
        verify(insuranceCompanyRepository).findById(1);
        verify(insurancePlanRepository).save(any(InsurancePlan.class));
    }
    
    @Test
    void testUpdatePlan_PlanNotFound() {

        when(insurancePlanRepository.findById(100))
                .thenReturn(Optional.empty());

        InsurancePlanNotFoundException exception = assertThrows(
                InsurancePlanNotFoundException.class,
                () -> insurancePlanService.updatePlan(100, planDTO));

        assertEquals("Insurance Plan not found with ID: 100",
                exception.getMessage());

        verify(insurancePlanRepository).findById(100);
        verify(insurancePlanRepository, never())
                .save(any(InsurancePlan.class));
    }
    
    @Test
    void testUpdatePlan_CompanyNotFound() {

        when(insurancePlanRepository.findById(1))
                .thenReturn(Optional.of(plan));

        planDTO.setCompanyId(100);

        when(insuranceCompanyRepository.findById(100))
                .thenReturn(Optional.empty());

        InsuranceCompanyNotFoundException exception = assertThrows(
                InsuranceCompanyNotFoundException.class,
                () -> insurancePlanService.updatePlan(1, planDTO));

        assertEquals("Insurance Company not found with ID: 100",
                exception.getMessage());

        verify(insurancePlanRepository).findById(1);
        verify(insuranceCompanyRepository).findById(100);
        verify(insurancePlanRepository, never())
                .save(any(InsurancePlan.class));
    }

    @Test
    void testDeletePlan() {

        when(insurancePlanRepository.findById(1))
                .thenReturn(Optional.of(plan));

        insurancePlanService.deletePlan(1);

        verify(insurancePlanRepository).findById(1);
        verify(insurancePlanRepository).delete(plan);
    }
    
    @Test
    void testDeletePlan_PlanNotFound() {

        when(insurancePlanRepository.findById(100))
                .thenReturn(Optional.empty());

        InsurancePlanNotFoundException exception = assertThrows(
                InsurancePlanNotFoundException.class,
                () -> insurancePlanService.deletePlan(100));

        assertEquals("Insurance Plan not found with ID: 100",
                exception.getMessage());

        verify(insurancePlanRepository).findById(100);
        verify(insurancePlanRepository, never())
                .delete(any(InsurancePlan.class));
    }

}
