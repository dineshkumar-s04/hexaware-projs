package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careassist.dto.NotificationDTO;
import com.hexaware.careassist.entity.Notification;
import com.hexaware.careassist.entity.User;
import com.hexaware.careassist.exception.UserNotFoundException;
import com.hexaware.careassist.repository.NotificationRepository;
import com.hexaware.careassist.repository.UserRepository;
import com.hexaware.careassist.service.INotificationService;

@Service
public class NotificationServiceImpl implements INotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public NotificationDTO sendNotification(NotificationDTO notificationDTO) {

		logger.info("Sending notification to user id {}", notificationDTO.getUserId());

		User user = userRepository.findById(notificationDTO.getUserId()).orElseThrow(() -> {

			logger.warn("User not found with id {}", notificationDTO.getUserId());

			return new UserNotFoundException("User not found with ID: " + notificationDTO.getUserId());
		});

		Notification notification = new Notification();

		notification.setUser(user);
		notification.setMessage(notificationDTO.getMessage());
		notification.setType(notificationDTO.getType());
		notification.setRead(notificationDTO.isRead());

		Notification savedNotification = notificationRepository.save(notification);

		logger.info("Notification sent successfully with id {}", savedNotification.getNotificationId());

		NotificationDTO dto = new NotificationDTO();

		dto.setNotificationId(savedNotification.getNotificationId());
		dto.setUserId(savedNotification.getUser().getUserId());
		dto.setMessage(savedNotification.getMessage());
		dto.setType(savedNotification.getType());
		dto.setRead(savedNotification.isRead());

		return dto;
	}

	@Override
	public List<NotificationDTO> getNotificationsByUser(Integer userId) {

		logger.info("Fetching notifications for user id {}", userId);

		User user = userRepository.findById(userId).orElseThrow(() -> {

			logger.warn("User not found with id {}", userId);

			return new UserNotFoundException("User not found with ID: " + userId);
		});

		List<Notification> notifications = notificationRepository.findAll();

		List<NotificationDTO> dtoList = new ArrayList<>();

		for (Notification notification : notifications) {

			if (notification.getUser().getUserId() == user.getUserId()) {
				
				NotificationDTO dto = new NotificationDTO();

				dto.setNotificationId(notification.getNotificationId());
				dto.setUserId(notification.getUser().getUserId());
				dto.setMessage(notification.getMessage());
				dto.setType(notification.getType());
				dto.setRead(notification.isRead());

				dtoList.add(dto);
			}
		}

		logger.info("Total notifications fetched for user {}: {}", userId, dtoList.size());

		return dtoList;
	}
}