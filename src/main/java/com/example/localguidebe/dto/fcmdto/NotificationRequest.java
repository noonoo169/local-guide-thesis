package com.example.localguidebe.dto.fcmdto;

import jakarta.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class NotificationRequest {
  @NotBlank private String title;
  @NotBlank private String body;
  private Map<String, String> data = new HashMap<>();
}
