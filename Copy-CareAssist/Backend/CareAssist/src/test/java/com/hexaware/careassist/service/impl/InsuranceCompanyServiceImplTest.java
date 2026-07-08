package com.hexaware.careassist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexaware.careassist.dto.InsuranceCompanyDTO;
import com.hexaware.careassist.entity.InsuranceCompany;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.InsuranceCompanyNotFoundException;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.InsuranceCompanyRepository;
import com.hexaware.careassist.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class InsuranceCompanyServiceImplTest {

    @Mock
    private InsuranceCompanyRepository insuranceCompanyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InsuranceCompanyServiceImpl insuranceCompanyService;

    private InsuranceCompany company;
    private InsuranceCompanyDTO companyDTO;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUserId(1);
        user.setName("Dinesh");
        user.setEmail("dinesh@gmail.com");

        company = new InsuranceCompany();
        company.setCompanyId(1);
        company.setUser(user);
        company.setCompanyName("Star Health");
        company.setLicenseNumber("LIC12345");
        company.setAddress("Chennai");

        companyDTO = new InsuranceCompanyDTO();
        companyDTO.setCompanyId(1);
        companyDTO.setUserId(1);
        companyDTO.setCompanyName("Star Health");
        companyDTO.setLicenseNumber("LIC12345");
        companyDTO.setAddress("Chennai");
    }
    
    @Test
    void testAddCompany() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(insuranceCompanyRepository.save(any(InsuranceCompany.class)))
                .thenReturn(company);

        // Act
        InsuranceCompanyDTO result = insuranceCompanyService.addCompany(companyDTO);

        // Assert
        assertNotNull(result);
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(company.getCompanyName(), result.getCompanyName());
        assertEquals(company.getLicenseNumber(), result.getLicenseNumber());
        assertEquals(company.getAddress(), result.getAddress());

        verify(userRepository, times(1)).findById(1);
        verify(insuranceCompanyRepository, times(1)).save(any(InsuranceCompany.class));
    }
    
    @Test
    void testAddCompany_UserNotFound() {

        // Arrange
        companyDTO.setUserId(100);

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> insuranceCompanyService.addCompany(companyDTO));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(userRepository).findById(100);
        verify(insuranceCompanyRepository, never()).save(any(InsuranceCompany.class));
    }

    @Test
    void testGetCompanyById() {

        // Arrange
        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        // Act
        InsuranceCompanyDTO result = insuranceCompanyService.getCompanyById(1);

        // Assert
        assertNotNull(result);
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(company.getCompanyName(), result.getCompanyName());
        assertEquals(company.getLicenseNumber(), result.getLicenseNumber());
        assertEquals(company.getAddress(), result.getAddress());

        verify(insuranceCompanyRepository).findById(1);
    }
    
    @Test
    void testGetCompanyById_CompanyNotFound() {

        // Arrange
        when(insuranceCompanyRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        InsuranceCompanyNotFoundException exception = assertThrows(
                InsuranceCompanyNotFoundException.class,
                () -> insuranceCompanyService.getCompanyById(100));

        assertEquals("Insurance Company not found with ID: 100",
                exception.getMessage());

        verify(insuranceCompanyRepository).findById(100);
    }

    @Test
    void testGetAllCompanies() {

        // Arrange
        List<InsuranceCompany> companies = new ArrayList<>();
        companies.add(company);

        when(insuranceCompanyRepository.findAll()).thenReturn(companies);

        // Act
        List<InsuranceCompanyDTO> result = insuranceCompanyService.getAllCompanies();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        InsuranceCompanyDTO dto = result.get(0);

        assertEquals(company.getCompanyId(), dto.getCompanyId());
        assertEquals(user.getUserId(), dto.getUserId());
        assertEquals(company.getCompanyName(), dto.getCompanyName());
        assertEquals(company.getLicenseNumber(), dto.getLicenseNumber());
        assertEquals(company.getAddress(), dto.getAddress());

        verify(insuranceCompanyRepository).findAll();
    }

    @Test
    void testUpdateCompany() {

        // Arrange
        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(insuranceCompanyRepository.save(any(InsuranceCompany.class)))
                .thenReturn(company);

        // Act
        InsuranceCompanyDTO result =
                insuranceCompanyService.updateCompany(1, companyDTO);

        // Assert
        assertNotNull(result);
        assertEquals(company.getCompanyId(), result.getCompanyId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(company.getCompanyName(), result.getCompanyName());
        assertEquals(company.getLicenseNumber(), result.getLicenseNumber());
        assertEquals(company.getAddress(), result.getAddress());

        verify(insuranceCompanyRepository).findById(1);
        verify(userRepository).findById(1);
        verify(insuranceCompanyRepository).save(any(InsuranceCompany.class));
    }
    
    @Test
    void testUpdateCompany_CompanyNotFound() {

        when(insuranceCompanyRepository.findById(100))
                .thenReturn(Optional.empty());

        InsuranceCompanyNotFoundException exception = assertThrows(
                InsuranceCompanyNotFoundException.class,
                () -> insuranceCompanyService.updateCompany(100, companyDTO));

        assertEquals("Insurance Company not found with ID: 100",
                exception.getMessage());

        verify(insuranceCompanyRepository).findById(100);
        verify(insuranceCompanyRepository, never())
                .save(any(InsuranceCompany.class));
    }
    
    @Test
    void testUpdateCompany_UserNotFound() {

        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        companyDTO.setUserId(100);

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> insuranceCompanyService.updateCompany(1, companyDTO));

        assertEquals("User not found with ID: 100",
                exception.getMessage());

        verify(insuranceCompanyRepository).findById(1);
        verify(userRepository).findById(100);
        verify(insuranceCompanyRepository, never())
                .save(any(InsuranceCompany.class));
    }

    @Test
    void testDeleteCompany() {

        when(insuranceCompanyRepository.findById(1))
                .thenReturn(Optional.of(company));

        insuranceCompanyService.deleteCompany(1);

        verify(insuranceCompanyRepository).findById(1);
        verify(insuranceCompanyRepository).delete(company);
    }
    
    @Test
    void testDeleteCompany_CompanyNotFound() {

        when(insuranceCompanyRepository.findById(100))
                .thenReturn(Optional.empty());

        InsuranceCompanyNotFoundException exception = assertThrows(
                InsuranceCompanyNotFoundException.class,
                () -> insuranceCompanyService.deleteCompany(100));

        assertEquals("Insurance Company not found with ID: 100",
                exception.getMessage());

        verify(insuranceCompanyRepository).findById(100);
        verify(insuranceCompanyRepository, never())
                .delete(any(InsuranceCompany.class));
    }

}
