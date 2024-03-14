package com.example.localguidebe.service;

import com.example.localguidebe.dto.NotificationDTO;
import java.util.List;

public interface NotificationService {
  List<NotificationDTO> getNotifications(Integer page, Integer limit, String email);
}
