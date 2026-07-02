package com.hexaware.careassist.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

import com.hexaware.careassist.dto.PatientDTO;
import com.hexaware.careassist.entity.Patient;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.PatientNotFoundException;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.PatientRepository;
import com.hexaware.careassist.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient patient;
    private PatientDTO patientDTO;
    private User user;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUserId(1);
        user.setName("Dinesh");
        user.setEmail("dinesh@gmail.com");

        patient = new Patient();
        patient.setPatientId(1);
        patient.setUser(user);
        patient.setDob(LocalDate.of(2002, 5, 10));
        patient.setGender("Male");
        patient.setSymptoms("Fever");
        patient.setTreatment("Medicine");
        patient.setAddress("Chennai");

        patientDTO = new PatientDTO();
        patientDTO.setPatientId(1);
        patientDTO.setUserId(1);
        patientDTO.setDob(LocalDate.of(2002, 5, 10));
        patientDTO.setGender("Male");
        patientDTO.setSymptoms("Fever");
        patientDTO.setTreatment("Medicine");
        patientDTO.setAddress("Chennai");
    }
    
    @Test
    void testRegisterPatient() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(patientRepository.save(any(Patient.class)))
                .thenReturn(patient);

        // Act
        PatientDTO result = patientService.registerPatient(patientDTO);

        // Assert
        assertNotNull(result);
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(patient.getDob(), result.getDob());
        assertEquals(patient.getGender(), result.getGender());
        assertEquals(patient.getSymptoms(), result.getSymptoms());
        assertEquals(patient.getTreatment(), result.getTreatment());
        assertEquals(patient.getAddress(), result.getAddress());

        verify(userRepository, times(1)).findById(1);
        verify(patientRepository, times(1)).save(any(Patient.class));
    }
    
    @Test
    void testRegisterPatient_UserNotFound() {

        // Arrange
        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        patientDTO.setUserId(100);

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> patientService.registerPatient(patientDTO));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(userRepository, times(1)).findById(100);
        verify(patientRepository, never()).save(any(Patient.class));
    }
    
    @Test
    void testGetAllPatients() {

        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(patient);

        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<PatientDTO> result = patientService.getAllPatients();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        PatientDTO dto = result.get(0);

        assertEquals(patient.getPatientId(), dto.getPatientId());
        assertEquals(user.getUserId(), dto.getUserId());
        assertEquals(patient.getDob(), dto.getDob());
        assertEquals(patient.getGender(), dto.getGender());
        assertEquals(patient.getSymptoms(), dto.getSymptoms());
        assertEquals(patient.getTreatment(), dto.getTreatment());
        assertEquals(patient.getAddress(), dto.getAddress());

        verify(patientRepository, times(1)).findAll();
    }
    
    @Test
    void testUpdatePatient() {

        // Arrange
        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(patientRepository.save(any(Patient.class)))
                .thenReturn(patient);

        // Act
        PatientDTO result = patientService.updatePatient(1, patientDTO);

        // Assert
        assertNotNull(result);
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(patient.getDob(), result.getDob());
        assertEquals(patient.getGender(), result.getGender());
        assertEquals(patient.getSymptoms(), result.getSymptoms());
        assertEquals(patient.getTreatment(), result.getTreatment());
        assertEquals(patient.getAddress(), result.getAddress());

        verify(patientRepository).findById(1);
        verify(userRepository).findById(1);
        verify(patientRepository).save(any(Patient.class));
    }
    
    @Test
    void testUpdatePatient_PatientNotFound() {

        when(patientRepository.findById(100))
                .thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> patientService.updatePatient(100, patientDTO));

        assertEquals("Patient not found with ID: 100", exception.getMessage());

        verify(patientRepository).findById(100);
        verify(patientRepository, never()).save(any(Patient.class));
    }
    
    @Test
    void testUpdatePatient_UserNotFound() {

        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        patientDTO.setUserId(100);

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> patientService.updatePatient(1, patientDTO));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(patientRepository).findById(1);
        verify(userRepository).findById(100);
        verify(patientRepository, never()).save(any(Patient.class));
    }
    
    @Test
    void testDeletePatient() {

        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        patientService.deletePatient(1);

        verify(patientRepository).findById(1);
        verify(patientRepository).delete(patient);
    }
    
    @Test
    void testDeletePatient_PatientNotFound() {

        when(patientRepository.findById(100))
                .thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> patientService.deletePatient(100));

        assertEquals("Patient not found with ID: 100", exception.getMessage());

        verify(patientRepository).findById(100);
        verify(patientRepository, never()).delete(any(Patient.class));
    }

    @Test
    void testGetPatientById() {

        when(patientRepository.findById(1))
                .thenReturn(Optional.of(patient));

        PatientDTO result = patientService.getPatientById(1);

        assertNotNull(result);
        assertEquals(patient.getPatientId(), result.getPatientId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(patient.getDob(), result.getDob());
        assertEquals(patient.getGender(), result.getGender());
        assertEquals(patient.getSymptoms(), result.getSymptoms());
        assertEquals(patient.getTreatment(), result.getTreatment());
        assertEquals(patient.getAddress(), result.getAddress());

        verify(patientRepository).findById(1);
    }
    
    @Test
    void testGetPatientById_PatientNotFound() {

        when(patientRepository.findById(100))
                .thenReturn(Optional.empty());

        PatientNotFoundException exception = assertThrows(
                PatientNotFoundException.class,
                () -> patientService.getPatientById(100));

        assertEquals("Patient not found with ID: 100", exception.getMessage());

        verify(patientRepository).findById(100);
    }

    

}