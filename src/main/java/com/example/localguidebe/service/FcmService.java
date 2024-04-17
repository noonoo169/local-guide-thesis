package com.example.localguidebe.service;

import com.example.localguidebe.dto.fcmdto.AllDevicesNotificationRequest;
import com.example.localguidebe.dto.fcmdto.DeviceNotificationRequest;
import com.example.localguidebe.dto.fcmdto.NotificationSubscriptionRequest;
import com.example.localguidebe.dto.fcmdto.TopicNotificationRequest;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.FirebaseMessagingException;
import java.util.concurrent.ExecutionException;

public interface FcmService {
  void sendNotificationToDevice(DeviceNotificationRequest request)
      throws FirebaseMessagingException, ExecutionException, InterruptedException;

  void sendPushNotificationToTopic(TopicNotificationRequest request)
      throws FirebaseMessagingException, ExecutionException, InterruptedException;

  void sendMulticastNotification(AllDevicesNotificationRequest request)
      throws FirebaseMessagingException;

  void subscribeDeviceToTopic(NotificationSubscriptionRequest request)
      throws FirebaseMessagingException;

  void unsubscribeDeviceFromTopic(NotificationSubscriptionRequest request)
      throws FirebaseMessagingException;

  AndroidConfig getAndroidConfig(String topic);

  ApnsConfig getApnsConfig(String topic);
}
