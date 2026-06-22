package com.hexaware.careassist.service.impl;

import java.util.ArrayList;
import java.util.List;

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

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public NotificationDTO sendNotification(NotificationDTO notificationDTO) {

		User user = userRepository.findById(notificationDTO.getUserId())
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + notificationDTO.getUserId()));

		Notification notification = new Notification();

		notification.setUser(user);
		notification.setMessage(notificationDTO.getMessage());
		notification.setType(notificationDTO.getType());
		notification.setRead(notificationDTO.isRead());

		Notification savedNotification = notificationRepository.save(notification);

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

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

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

		return dtoList;
	}

}
