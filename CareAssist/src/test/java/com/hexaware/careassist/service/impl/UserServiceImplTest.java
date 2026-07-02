package com.hexaware.careassist.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hexaware.careassist.dto.UserDTO;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUserId(1);
        user.setName("Dinesh");
        user.setEmail("dinesh@gmail.com");
        user.setPassword("encodedPassword");
        user.setPhone("9876543210");
        user.setRole("PATIENT");
        user.setAccountStatus("ACTIVE");
        user.setProfilePic("profile.jpg");

        userDTO = new UserDTO();
        userDTO.setUserId(1);
        userDTO.setName("Dinesh");
        userDTO.setEmail("dinesh@gmail.com");
        userDTO.setPassword("password123");
        userDTO.setPhone("9876543210");
        userDTO.setRole("PATIENT");
        userDTO.setAccountStatus("ACTIVE");
        userDTO.setProfilePic("profile.jpg");
    }

    @Test
    void testRegisterUser() {

        // Arrange
        when(passwordEncoder.encode(userDTO.getPassword()))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        // Act
        UserDTO savedUser = userService.registerUser(userDTO);

        // Assert
        assertNotNull(savedUser);
        assertEquals(user.getUserId(), savedUser.getUserId());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getEmail(), savedUser.getEmail());
        assertEquals(user.getPassword(), savedUser.getPassword());
        assertEquals(user.getPhone(), savedUser.getPhone());
        assertEquals(user.getRole(), savedUser.getRole());
        assertEquals(user.getAccountStatus(), savedUser.getAccountStatus());
        assertEquals(user.getProfilePic(), savedUser.getProfilePic());

        verify(passwordEncoder, times(1))
                .encode(userDTO.getPassword());

        verify(userRepository, times(1))
                .save(any(User.class));
    }

    @Test
    void testGetUserById() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        // Act
        UserDTO result = userService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getPhone(), result.getPhone());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getAccountStatus(), result.getAccountStatus());
        assertEquals(user.getProfilePic(), result.getProfilePic());

        verify(userRepository, times(1)).findById(1);
    }
    
    @Test
    void testGetUserById_UserNotFound() {

        // Arrange
        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(100));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(userRepository, times(1)).findById(100);
    }

    @Test
    void testGetAllUsers() {

        // Arrange
        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDTO> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        UserDTO dto = result.get(0);

        assertEquals(user.getUserId(), dto.getUserId());
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPassword(), dto.getPassword());
        assertEquals(user.getPhone(), dto.getPhone());
        assertEquals(user.getRole(), dto.getRole());
        assertEquals(user.getAccountStatus(), dto.getAccountStatus());
        assertEquals(user.getProfilePic(), dto.getProfilePic());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.encode(userDTO.getPassword()))
                .thenReturn("encodedPassword");

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        // Act
        UserDTO result = userService.updateUser(1, userDTO);

        // Assert
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getPhone(), result.getPhone());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getAccountStatus(), result.getAccountStatus());
        assertEquals(user.getProfilePic(), result.getProfilePic());

        verify(userRepository, times(1)).findById(1);
        verify(passwordEncoder, times(1)).encode(userDTO.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testUpdateUser_UserNotFound() {

        // Arrange
        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.updateUser(100, userDTO));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(userRepository, times(1)).findById(100);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        doNothing().when(userRepository).delete(user);

        // Act
        userService.deleteUser(1);

        // Assert
        verify(userRepository, times(1)).findById(1);
        verify(userRepository, times(1)).delete(user);
    }
    
    @Test
    void testDeleteUser_UserNotFound() {

        // Arrange
        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.deleteUser(100));

        assertEquals("User not found with ID: 100", exception.getMessage());

        verify(userRepository, times(1)).findById(100);
        verify(userRepository, never()).delete(any(User.class));
    }

}