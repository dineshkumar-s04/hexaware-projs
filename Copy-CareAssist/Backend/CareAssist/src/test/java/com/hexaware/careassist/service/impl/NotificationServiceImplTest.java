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

import com.hexaware.careassist.dto.NotificationDTO;
import com.hexaware.careassist.entity.Notification;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.NotificationRepository;
import com.hexaware.careassist.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private User user;
    private Notification notification;
    private NotificationDTO notificationDTO;

    @BeforeEach
    void setUp() {

        user = new User();
        user.setUserId(1);
        user.setName("Dinesh");

        notification = new Notification();
        notification.setNotificationId(1);
        notification.setUser(user);
        notification.setMessage("Claim Approved");
        notification.setType("CLAIM");
        notification.setRead(false);

        notificationDTO = new NotificationDTO();
        notificationDTO.setNotificationId(1);
        notificationDTO.setUserId(1);
        notificationDTO.setMessage("Claim Approved");
        notificationDTO.setType("CLAIM");
        notificationDTO.setRead(false);
    }

    @Test
    void testSendNotification() {

        // Arrange
        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(notificationRepository.save(any(Notification.class)))
                .thenReturn(notification);

        // Act
        NotificationDTO result =
                notificationService.sendNotification(notificationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(notification.getNotificationId(), result.getNotificationId());
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(notification.getMessage(), result.getMessage());
        assertEquals(notification.getType(), result.getType());
        assertEquals(notification.isRead(), result.isRead());

        verify(userRepository).findById(1);
        verify(notificationRepository).save(any(Notification.class));
    }
    
    @Test
    void testSendNotification_UserNotFound() {

        // Arrange
        notificationDTO.setUserId(100);

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> notificationService.sendNotification(notificationDTO));

        assertEquals("User not found with ID: 100",
                exception.getMessage());

        verify(userRepository).findById(100);
        verify(notificationRepository, never())
                .save(any(Notification.class));
    }

    @Test
    void testGetNotificationsByUser() {

        // Arrange
        List<Notification> notifications = new ArrayList<>();
        notifications.add(notification);

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        when(notificationRepository.findAll())
                .thenReturn(notifications);

        // Act
        List<NotificationDTO> result =
                notificationService.getNotificationsByUser(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());

        NotificationDTO dto = result.get(0);

        assertEquals(notification.getNotificationId(), dto.getNotificationId());
        assertEquals(user.getUserId(), dto.getUserId());
        assertEquals(notification.getMessage(), dto.getMessage());
        assertEquals(notification.getType(), dto.getType());
        assertEquals(notification.isRead(), dto.isRead());

        verify(userRepository).findById(1);
        verify(notificationRepository).findAll();
    }
    
    @Test
    void testGetNotificationsByUser_UserNotFound() {

        when(userRepository.findById(100))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> notificationService.getNotificationsByUser(100));

        assertEquals("User not found with ID: 100",
                exception.getMessage());

        verify(userRepository).findById(100);
        verify(notificationRepository, never()).findAll();
    }

}
