package com.example.localguidebe.converter;

import com.example.localguidebe.dto.NotificationDTO;
import com.example.localguidebe.entity.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationToNotificationDtoConverter {
  private final UserToUserDtoConverter userToUserDtoConverter;

  public NotificationToNotificationDtoConverter(UserToUserDtoConverter userToUserDtoConverter) {
    this.userToUserDtoConverter = userToUserDtoConverter;
  }

  public NotificationDTO convert(Notification source) {
    return new NotificationDTO(
        source.getId(),
        source.getMessage(),
        source.getNotificationDate(),
        source.isRead(),
        source.getNotificationType(),
        source.getAssociateId(),
        userToUserDtoConverter.convert(source.getSender()),
        userToUserDtoConverter.convert(source.getReceiver()));
  }
}
