package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.NotificationToNotificationDtoConverter;
import com.example.localguidebe.dto.NotificationDTO;
import com.example.localguidebe.dto.fcmdto.TopicNotificationRequest;
import com.example.localguidebe.entity.Notification;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.NotificationTypeEnum;
import com.example.localguidebe.repository.NotificationRepository;
import com.example.localguidebe.service.FcmService;
import com.example.localguidebe.service.NotificationService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.utils.JsonUtils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationToNotificationDtoConverter notificationToNotificationDtoConverter;
  private final UserService userService;
  private final FcmService fcmService;

  public NotificationServiceImpl(
      NotificationToNotificationDtoConverter notificationToNotificationDtoConverter,
      UserService userService,
      NotificationRepository notificationRepository,
      FcmService fcmService) {
    this.notificationRepository = notificationRepository;
    this.notificationToNotificationDtoConverter = notificationToNotificationDtoConverter;
    this.userService = userService;
    this.fcmService = fcmService;
  }

  @Override
  public List<NotificationDTO> getNotifications(Integer page, Integer limit, String email) {
    Sort sort = Sort.by("notificationDate").descending();
    Pageable paging = PageRequest.of(page, limit, sort);
    return notificationRepository.getNotificationsByReceiverEmail(email, paging).stream()
        .map(notificationToNotificationDtoConverter::convert)
        .toList();
  }

  @Override
  public Notification addNotification(
      Long receiverId,
      Long senderId,
      Long associateId,
      NotificationTypeEnum notificationTypeEnum,
      String message) {
    Notification newNotification = Notification.builder().build();
    if (receiverId != null) {
      User receiver = userService.findById(receiverId).orElse(null);
      newNotification.setReceiver(receiver);
    }
    if (senderId != null) {
      User sender = userService.findById(senderId).orElse(null);
      newNotification.setSender(sender);
    }

    newNotification.setNotificationDate(LocalDateTime.now());
    newNotification.setAssociateId(associateId);
    newNotification.setNotificationType(notificationTypeEnum);
    newNotification.setMessage(message);
    return notificationRepository.save(newNotification);
  }

  @Override
  public boolean updateIsReadNotification(Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId).orElse(null);
    if (notification == null) return false;
    notification.setRead(true);
    notificationRepository.save(notification);
    return true;
  }

  @Override
  public void sendToUser(TopicNotificationRequest topicNotificationRequest) {
    try {
      fcmService.sendPushNotificationToTopic(topicNotificationRequest);
    } catch (Exception e) {
      log.error(e.getLocalizedMessage());
    }
  }

  @Override
  public void sendTourStatusNotificationToGuideAndTravelerHaveCreateTourRequest(
      Tour tour,
      NotificationTypeEnum notificationTypeEnumOfGuide,
      String notificationMessageOfGuide,
      NotificationTypeEnum notificationTypeEnumOfTraveler,
      String notificationMessageOfTraveler) {
    User guide = tour.getGuide();
    Notification guideNotification =
        addNotification(
            guide.getId(),
            null,
            tour.getId(),
            notificationTypeEnumOfGuide,
            notificationMessageOfGuide);

    User traveler = null;
    Notification travelerNotification = null;
    if (tour.getIsForSpecificTraveler()) {
      traveler =
          Objects.requireNonNull(
                  guide.getTravelerRequestsOfGuide().stream()
                      .filter(
                          travelerRequest ->
                              travelerRequest.getTour() != null
                                  && travelerRequest.getTour().getId().equals(tour.getId()))
                      .findFirst()
                      .orElse(null))
              .getTraveler();
      travelerNotification =
          addNotification(
              traveler.getId(),
              null,
              tour.getId(),
              notificationTypeEnumOfTraveler,
              notificationMessageOfTraveler);
    }

    try {
      // send to guide
      TopicNotificationRequest topicNotificationRequestOfGuide =
          TopicNotificationRequest.builder()
              .topicName(guide.getEmail().substring(0, guide.getEmail().indexOf("@")))
              .title(notificationTypeEnumOfGuide.toString())
              .body(
                  JsonUtils.convertObjectToJson(
                      notificationToNotificationDtoConverter.convert(guideNotification)))
              .build();
      sendToUser(topicNotificationRequestOfGuide);

      // send to traveler
      if (tour.getIsForSpecificTraveler() && traveler != null) {
        TopicNotificationRequest topicNotificationRequestOfTraveler =
            TopicNotificationRequest.builder()
                .topicName(traveler.getEmail().substring(0, traveler.getEmail().indexOf("@")))
                .title(notificationTypeEnumOfTraveler.toString())
                .body(
                    JsonUtils.convertObjectToJson(
                        notificationToNotificationDtoConverter.convert(travelerNotification)))
                .build();
        sendToUser(topicNotificationRequestOfTraveler);
      }
    } catch (Exception e) {
      log.error("Cannot send notification to user " + e.getLocalizedMessage());
    }
  }

  @Override
  public void
      sendNotificationForNewBookingOrRefundBookingOrReviewOnGuideOrReviewOnTourOrNewTravelerRequestToGuide(
          User guide,
          User traveler,
          Long guideIdOrTourIdOrBookingIdOrTravelerRequestId,
          NotificationTypeEnum notificationTypeEnum,
          String notificationMessage) {
    // notification send to guide
    Notification guideNotification =
        addNotification(
            guide.getId(),
            traveler.getId(),
            guideIdOrTourIdOrBookingIdOrTravelerRequestId,
            notificationTypeEnum,
            notificationMessage);
    try {
      TopicNotificationRequest topicNotificationRequest =
          TopicNotificationRequest.builder()
              .topicName(guide.getEmail().substring(0, guide.getEmail().indexOf("@")))
              .title(notificationTypeEnum.toString())
              .body(
                  JsonUtils.convertObjectToJson(
                      notificationToNotificationDtoConverter.convert(guideNotification)))
              .build();
      sendToUser(topicNotificationRequest);
    } catch (Exception e) {
      log.error("Cannot send notification to user " + e.getLocalizedMessage());
    }
  }

  @Override
  public List<Notification> getIsNotReadNotifications(String email) {
    return notificationRepository.getIsNotReadNotifications(email);
  }
}
