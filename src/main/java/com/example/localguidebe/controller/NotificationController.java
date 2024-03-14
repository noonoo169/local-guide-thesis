package com.example.localguidebe.controller;

import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.NotificationService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
  private final NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping("")
  public ResponseEntity<Result> getNotification(
      Authentication authentication,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer limit) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            String email = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        true,
                        HttpStatus.OK.value(),
                        "Get notifications successfully",
                        notificationService.getNotifications(page, limit, email)));
          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    new Result(
                        false,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Get notifications successfully"));
          }
        });
  }
}
