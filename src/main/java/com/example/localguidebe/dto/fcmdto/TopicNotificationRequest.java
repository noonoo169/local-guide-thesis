package com.example.localguidebe.dto.fcmdto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class TopicNotificationRequest extends NotificationRequest {
  @NotBlank private String topicName;
}
