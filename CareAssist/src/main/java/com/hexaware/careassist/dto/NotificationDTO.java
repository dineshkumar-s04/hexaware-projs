package com.hexaware.careassist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {

	
	private int notificationId;
	private int userId;
	private String message;
	private String type;
	private boolean isRead;
}
