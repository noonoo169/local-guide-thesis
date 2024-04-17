package com.example.localguidebe.dto.fcmdto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class AllDevicesNotificationRequest extends NotificationRequest {
  List<String> deviceTokenList = new ArrayList<>();
}
