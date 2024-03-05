package com.example.localguidebe.service;

public interface EmailService {
  boolean sendEmailToTraveler(Long cartId, String recipientEmail);
}
