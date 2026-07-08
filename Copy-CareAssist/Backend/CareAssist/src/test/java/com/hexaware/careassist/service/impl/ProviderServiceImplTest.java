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

import com.hexaware.careassist.dto.ProviderDTO;
import com.hexaware.careassist.entity.Provider;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.ProviderNotFoundException;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.ProviderRepository;
import com.hexaware.careassist.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {

    @Mock
    private ProviderRepository providerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProviderServiceImpl providerService;

    private Provider provider;
    private ProviderDTO providerDTO;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUserId(1);
        user.setName("Dinesh");
        user.setEmail("dinesh@gmail.com");

        provider = new Provider();
        provider.setProviderId(1);
        provider.setUser(user);
        provider.setHospitalName("Apollo Hospital");
        provider.setLicenseNumber("LIC12345");
        provider.setSpecialization("Cardiology");
        provider.setAddress("Chennai");

        providerDTO = new ProviderDTO();
        providerDTO.setProviderId(1);
        providerDTO.setUserId(1);
        providerDTO.setHospitalName("Apollo Hospital");
        providerDTO.setLicenseNumber("LIC12345");
        providerDTO.setSpecialization("Cardiology");
        providerDTO.setAddress("Chennai");
    }
    
    @Test
    void testAddProvider() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(providerRepository.save(any(Provider.class)))
                .thenReturn(provider);

        // Act
        ProviderDTO result = providerService.addProvider(providerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(provider.getProviderId(), result.getProviderId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(provider.getHospitalName(), result.getHospitalName());
        assertEquals(provider.getLicenseNumber(), result.getLicenseNumber());
        assertEquals(provider.getSpecialization(), result.getSpecialization());
        assertEquals(provider.getAddress(), result.getAddress());

        verify(userRepository, times(1)).findById(1);
        verify(providerRepository, times(1)).save(any(Provider.class));
    }
    
    @Test
    void testAddProvider_UserNotFound() {

        // Arrange
        providerDTO.setUserId(100);

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> providerService.addProvider(providerDTO));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(userRepository).findById(100);
        verify(providerRepository, never()).save(any(Provider.class));
    }
    
    @Test
    void testGetProviderById() {

        // Arrange
        when(providerRepository.findById(1))
                .thenReturn(Optional.of(provider));

        // Act
        ProviderDTO result = providerService.getProviderById(1);

        // Assert
        assertNotNull(result);
        assertEquals(provider.getProviderId(), result.getProviderId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(provider.getHospitalName(), result.getHospitalName());
        assertEquals(provider.getLicenseNumber(), result.getLicenseNumber());
        assertEquals(provider.getSpecialization(), result.getSpecialization());
        assertEquals(provider.getAddress(), result.getAddress());

        verify(providerRepository).findById(1);
    }
    
    @Test
    void testGetProviderById_ProviderNotFound() {

        // Arrange
        when(providerRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        ProviderNotFoundException exception = assertThrows(
                ProviderNotFoundException.class,
                () -> providerService.getProviderById(100));

        assertEquals("Provider not found with ID: 100", exception.getMessage());

        verify(providerRepository).findById(100);
    }
    
    @Test
    void testGetAllProviders() {

        // Arrange
        List<Provider> providers = new ArrayList<>();
        providers.add(provider);

        when(providerRepository.findAll()).thenReturn(providers);

        // Act
        List<ProviderDTO> result = providerService.getAllProviders();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        ProviderDTO dto = result.get(0);

        assertEquals(provider.getProviderId(), dto.getProviderId());
        assertEquals(user.getUserId(), dto.getUserId());
        assertEquals(provider.getHospitalName(), dto.getHospitalName());
        assertEquals(provider.getLicenseNumber(), dto.getLicenseNumber());
        assertEquals(provider.getSpecialization(), dto.getSpecialization());
        assertEquals(provider.getAddress(), dto.getAddress());

        verify(providerRepository).findAll();
    }
    
    @Test
    void testUpdateProvider() {

        // Arrange
        when(providerRepository.findById(1))
                .thenReturn(Optional.of(provider));

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(providerRepository.save(any(Provider.class)))
                .thenReturn(provider);

        // Act
        ProviderDTO result = providerService.updateProvider(1, providerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(provider.getProviderId(), result.getProviderId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(provider.getHospitalName(), result.getHospitalName());
        assertEquals(provider.getLicenseNumber(), result.getLicenseNumber());
        assertEquals(provider.getSpecialization(), result.getSpecialization());
        assertEquals(provider.getAddress(), result.getAddress());

        verify(providerRepository).findById(1);
        verify(userRepository).findById(1);
        verify(providerRepository).save(any(Provider.class));
    }
    
    @Test
    void testUpdateProvider_ProviderNotFound() {

        when(providerRepository.findById(100))
                .thenReturn(Optional.empty());

        ProviderNotFoundException exception = assertThrows(
                ProviderNotFoundException.class,
                () -> providerService.updateProvider(100, providerDTO));

        assertEquals("Provider not found with ID: 100", exception.getMessage());

        verify(providerRepository).findById(100);
        verify(providerRepository, never()).save(any(Provider.class));
    }
    
    @Test
    void testUpdateProvider_UserNotFound() {

        when(providerRepository.findById(1))
                .thenReturn(Optional.of(provider));

        providerDTO.setUserId(100);

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> providerService.updateProvider(1, providerDTO));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(providerRepository).findById(1);
        verify(userRepository).findById(100);
        verify(providerRepository, never()).save(any(Provider.class));
    }
    
    @Test
    void testDeleteProvider() {

        when(providerRepository.findById(1))
                .thenReturn(Optional.of(provider));

        providerService.deleteProvider(1);

        verify(providerRepository).findById(1);
        verify(providerRepository).delete(provider);
    }
    
    @Test
    void testDeleteProvider_ProviderNotFound() {

        when(providerRepository.findById(100))
                .thenReturn(Optional.empty());

        ProviderNotFoundException exception = assertThrows(
                ProviderNotFoundException.class,
                () -> providerService.deleteProvider(100));

        assertEquals("Provider not found with ID: 100", exception.getMessage());

        verify(providerRepository).findById(100);
        verify(providerRepository, never()).delete(any(Provider.class));
    }

}