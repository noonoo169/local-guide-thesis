package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.fcmdto.AllDevicesNotificationRequest;
import com.example.localguidebe.dto.fcmdto.DeviceNotificationRequest;
import com.example.localguidebe.dto.fcmdto.NotificationSubscriptionRequest;
import com.example.localguidebe.dto.fcmdto.TopicNotificationRequest;
import com.example.localguidebe.service.FcmService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FcmServiceImpl implements FcmService {
  private final FirebaseApp firebaseApp;

  @Autowired
  public FcmServiceImpl(FirebaseApp firebaseApp) {
    this.firebaseApp = firebaseApp;
  }

  @Override
  public void sendNotificationToDevice(DeviceNotificationRequest request)
      throws FirebaseMessagingException, ExecutionException, InterruptedException {
    Message fcmMessage =
        Message.builder()
            .setToken(request.getDeviceToken())
            .setNotification(
                Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getBody())
                    .build())
            //            .putAllData(request.getData())
            .build();

    String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
    log.info("sendNotificationToDevice response: {}", response);
  }

  @Override
  public void sendPushNotificationToTopic(TopicNotificationRequest request)
      throws FirebaseMessagingException, ExecutionException, InterruptedException {
    Message fcmMessage =
        Message.builder()
            .setTopic(request.getTopicName())
            .setNotification(
                Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getBody())
                    .build())
                        .setAndroidConfig(getAndroidConfig(request.getTopicName()))
                        .setApnsConfig(getApnsConfig(request.getTopicName()))
            //            .putAllData(request.getData())
            .build();

    String response = FirebaseMessaging.getInstance(firebaseApp).sendAsync(fcmMessage).get();
    log.info("sendNotificationToTopic response: {}", response);
  }

  @Override
  public void sendMulticastNotification(AllDevicesNotificationRequest request)
      throws FirebaseMessagingException {
    MulticastMessage multicastMessage =
        MulticastMessage.builder()
            // .addAllTokens(request.getDeviceTokenList().isEmpty() ?
            // getAllDeviceTokens() : request.getDeviceTokenList())
            .addAllTokens(request.getDeviceTokenList())
            .setNotification(
                Notification.builder()
                    .setTitle(request.getTitle())
                    .setBody(request.getBody())
                    .build())
            .putAllData(request.getData())
            .build();

    BatchResponse response =
        FirebaseMessaging.getInstance(firebaseApp).sendEachForMulticast(multicastMessage);
    // Process the response
    for (SendResponse sendResponse : response.getResponses()) {
      if (sendResponse.isSuccessful()) {
        log.info("Message sent successfully to: {}", sendResponse.getMessageId());
      } else {
        log.info("Failed to send message to: {}", sendResponse.getMessageId());
        log.error("Error details: {}", sendResponse.getException().getMessage());
      }
    }
  }

  @Override
  public void subscribeDeviceToTopic(NotificationSubscriptionRequest request)
      throws FirebaseMessagingException {
    TopicManagementResponse topicManagementResponse =
        FirebaseMessaging.getInstance()
            .subscribeToTopic(
                Arrays.asList(request.getDeviceToken().split(",")), request.getTopicName());
    log.warn(
        "Error: " + topicManagementResponse.getErrors().toString(),
        topicManagementResponse.getSuccessCount());
    log.warn("SuccessCount:" + topicManagementResponse.getSuccessCount());
    log.warn("ErrorCount:" + topicManagementResponse.getFailureCount());
  }

  @Override
  public void unsubscribeDeviceFromTopic(NotificationSubscriptionRequest request)
      throws FirebaseMessagingException {
    TopicManagementResponse topicManagementResponse =
        FirebaseMessaging.getInstance()
            .unsubscribeFromTopic(
                Arrays.asList(request.getDeviceToken().split(",")), request.getTopicName());
    log.warn(
        "Error: " + topicManagementResponse.getErrors().toString(),
        topicManagementResponse.getSuccessCount());
    log.warn("SuccessCount:" + topicManagementResponse.getSuccessCount());
    log.warn("ErrorCount:" + topicManagementResponse.getFailureCount());
  }

  @Override
  public AndroidConfig getAndroidConfig(String topic) {
    return AndroidConfig.builder()
        .setTtl(Duration.ofMinutes(2).toMillis())
        .setCollapseKey(topic)
        .setPriority(AndroidConfig.Priority.HIGH)
        .setNotification(
            AndroidNotification.builder()
                .setSound("default")
                .setColor("#FFFF00")
                .setTag(topic)
                .build())
        .build();
  }

  @Override
  public ApnsConfig getApnsConfig(String topic) {
    return ApnsConfig.builder()
        .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build())
        .build();
  }
}
