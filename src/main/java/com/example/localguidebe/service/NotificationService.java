package com.example.localguidebe.service;


import com.example.localguidebe.dto.NotificationDTO;
import java.util.List;


import com.example.localguidebe.entity.Notification;
import com.example.localguidebe.enums.NotificationTypeEnum;

public interface NotificationService {
  List<NotificationDTO> getNotifications(Integer page, Integer limit, String email);

  Notification addNotification(
      Long receiverId,
      Long senderId,
      Long associateId,
      NotificationTypeEnum notificationTypeEnum,
      String message);

}
