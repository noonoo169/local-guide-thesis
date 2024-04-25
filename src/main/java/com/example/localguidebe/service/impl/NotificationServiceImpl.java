package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.NotificationToNotificationDtoConverter;
import com.example.localguidebe.dto.NotificationDTO;
import com.example.localguidebe.repository.NotificationRepository;
import com.example.localguidebe.service.NotificationService;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
  private final NotificationRepository notificationRepository;
  private final NotificationToNotificationDtoConverter notificationToNotificationDtoConverter;

  public NotificationServiceImpl(
      NotificationRepository notificationRepository,
      NotificationToNotificationDtoConverter notificationToNotificationDtoConverter) {
    this.notificationRepository = notificationRepository;
    this.notificationToNotificationDtoConverter = notificationToNotificationDtoConverter;
  }

  @Override
  public List<NotificationDTO> getNotifications(Integer page, Integer limit, String email) {
    Sort sort = Sort.by("notificationDate").descending();
    Pageable paging = PageRequest.of(page, limit, sort);
    return notificationRepository.getNotificationsByReceiverEmail(email, paging).stream()
        .map(notificationToNotificationDtoConverter::convert)
        .toList();
  }
}
