package com.example.localguidebe.service;

import com.example.localguidebe.dto.NotificationDTO;
import com.example.localguidebe.dto.fcmdto.TopicNotificationRequest;
import com.example.localguidebe.entity.Notification;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.NotificationTypeEnum;
import com.example.localguidebe.system.constants.NotificationMessage;
import java.util.List;

public interface NotificationService {
  List<NotificationDTO> getNotifications(Integer page, Integer limit, String email);

  Notification addNotification(
      Long receiverId,
      Long senderId,
      Long associateId,
      NotificationTypeEnum notificationTypeEnum,
      String message);

  boolean updateIsReadNotification(Long notificationId);

  void sendToUser(TopicNotificationRequest topicNotificationRequest);

  void sendTourStatusNotificationToGuideAndTravelerHaveCreateTourRequest(
      Tour tour,
      NotificationTypeEnum notificationTypeEnumOfGuide,
      String notificationMessageOfGuide,
      NotificationTypeEnum notificationTypeEnumOfTraveler,
      String notificationMessageOfTraveler);

  void
      sendNotificationForNewBookingOrRefundBookingOrReviewOnGuideOrReviewOnTourOrNewTravelerRequestToGuide(
          User guide,
          User traveler,
          Long guideIdOrTourIdOrBookingIdOrTravelerRequestId,
          NotificationTypeEnum notificationTypeEnum,
          String notificationMessage);

    List<Notification> getIsNotReadNotifications(String email);
}
