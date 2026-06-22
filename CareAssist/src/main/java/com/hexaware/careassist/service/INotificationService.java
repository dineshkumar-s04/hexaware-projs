package com.hexaware.careassist.service;

import java.util.List;
import com.hexaware.careassist.dto.NotificationDTO;

public interface INotificationService {

    NotificationDTO sendNotification(
            NotificationDTO notificationDTO);

    List<NotificationDTO> getNotificationsByUser(
            Integer userId);
}