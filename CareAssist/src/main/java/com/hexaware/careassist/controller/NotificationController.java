package com.hexaware.careassist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.careassist.dto.NotificationDTO;
import com.hexaware.careassist.service.INotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private INotificationService notificationService;

	@PostMapping
	public ResponseEntity<NotificationDTO> sendNotification(@RequestBody NotificationDTO notificationDTO) {

		NotificationDTO savedNotification = notificationService.sendNotification(notificationDTO);

		return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<NotificationDTO>> getNotificationsByUser(@PathVariable Integer userId) {

		List<NotificationDTO> notifications = notificationService.getNotificationsByUser(userId);

		return ResponseEntity.ok(notifications);
	}
}