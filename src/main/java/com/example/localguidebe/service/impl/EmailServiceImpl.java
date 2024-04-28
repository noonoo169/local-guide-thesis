package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.EmailDetails;
import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.service.EmailService;
import com.example.localguidebe.system.constants.EmailSubject;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  @Value("${frontend.host}")
  private String feHost;

  @Autowired
  public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  // Method 1
  // To send a simple email
  @Override
  public boolean sendSimpleMail(EmailDetails details) {
    // Try block to check for exceptions
    try {
      // Creating a simple mail message
      SimpleMailMessage mailMessage = new SimpleMailMessage();

      // Setting up necessary details
      mailMessage.setTo(details.recipient());
      mailMessage.setText(details.msgBody());
      mailMessage.setSubject(details.subject());

      // Sending the mail
      mailSender.send(mailMessage);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // To send a mime email with html body
  @Override
  public boolean sendMimeMail(EmailDetails emailDetails, String templateName, Context context) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      String htmlContent = templateEngine.process("email/new-booking", context);
      helper.setText(htmlContent, true);
      helper.setTo(emailDetails.recipient());
      helper.setSubject(emailDetails.subject());
      mailSender.send(mimeMessage);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  // Method 2
  // To send an email with attachment
  @Override
  public boolean sendMailWithAttachment(
      EmailDetails details, String templateName, Context context) {
    // Creating a mime message
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper mimeMessageHelper;
    try {
      // Setting multipart as true for attachments to be send
      mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
      mimeMessageHelper.setTo(details.recipient());
      mimeMessageHelper.setText(details.msgBody());
      mimeMessageHelper.setSubject(details.subject());

      // Adding the attachment
      FileSystemResource file = new FileSystemResource(new File(details.attachment()));

      mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);

      // Sending the mail
      mailSender.send(mimeMessage);
      return true;
    }

    // Catch block to handle MessagingException
    catch (MessagingException e) {

      // Display message when exception occurred
      return false;
    }
  }

  @Override
  public boolean sendEmailForNewBooking(Booking booking) {
    String recipient = booking.getTour().getGuide().getEmail();
    String subject = EmailSubject.NEW_BOOKING;
    EmailDetails emailDetails =
        EmailDetails.builder().recipient(recipient).subject(subject).build();
    Context context = new Context();
    context.setVariable("tourName", booking.getTour().getName());
    context.setVariable("numberOfTravelers", booking.getNumberTravelers());
    context.setVariable("startDate", booking.getStartDate());
    context.setVariable("totalPrice", booking.getPrice());
    return sendMimeMail(emailDetails, "email/new-booking", context);
  }

  @Override
  public boolean sendEmailForResetPassword(String email, String token) {
    String subject = EmailSubject.RESET_PASSWORD;
    String linkResetPassword = feHost + "/change-password-by-token" + "?token=" + token;
    EmailDetails emailDetails =
        EmailDetails.builder()
            .recipient(email)
            .subject(subject)
            .msgBody(
                "Please use this link to reset your password. Please note that the link will expire in 10 minutes.\n"
                    + linkResetPassword)
            .build();
    return sendSimpleMail(emailDetails);
  }
}
