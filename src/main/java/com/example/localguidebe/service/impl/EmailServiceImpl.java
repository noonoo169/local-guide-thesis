package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.enums.AssociateName;
import com.example.localguidebe.repository.CartRepository;
import com.example.localguidebe.service.EmailService;
import com.example.localguidebe.service.ImageService;
import com.example.localguidebe.utils.EmailUtils;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
  @Autowired private final JavaMailSender mailSender;
  private final EmailUtils emailUtils = new EmailUtils();
  private final CartRepository cartRepository;

  private final ImageService imageService;

  @Autowired
  public EmailServiceImpl(
      JavaMailSender mailSender, CartRepository cartRepository, ImageService imageService) {
    this.mailSender = mailSender;
    this.cartRepository = cartRepository;
    this.imageService = imageService;
  }

  @Override
  public boolean sendEmailToTraveler(Long cartId, String recipientEmail) {
    Cart cart = cartRepository.findById(cartId).orElseThrow();

    MimeMessage message = mailSender.createMimeMessage();
    String htmlTemplate = null;
    StringBuilder CartInfo = new StringBuilder();
    StringBuilder tourImage = new StringBuilder();
    for (Booking booking : cart.getBookings()) {
      CartInfo.append("<tr>");
      CartInfo.append(
          "<td style='border: 1px solid black; padding: 30px;' class=\"esd-block-image\" align=\"center\" style=\"font-size:0\">");
      CartInfo.append(
          "<img src=\""
              + imageService
                  .getImageByAssociateIddAndAssociateName(
                      booking.getTour().getId(), AssociateName.TOUR)
                  .get(0)
                  .getImageLink()
              + "\" alt=\"Dinosaur Plush Toys\" class=\"adapt-img\" title=\"Dinosaur Plush Toys\" style=\"display: block;\">");
      CartInfo.append("</td>");
      CartInfo.append(
              "<td style='border: 1px solid black; padding: 30px;'><p style='font-weight: bold;'> ")
          .append(booking.getTour().getName())
          .append("</p></td>");
      CartInfo.append(
              "<td style='border: 1px solid black; padding: 30px;'><p style='font-weight: bold;'>")
          .append(booking.getNumberTravelers())
          .append(" Người </p></td>");
      CartInfo.append(
              "<td style='border: 1px solid black; padding: 30px;'><p style='font-weight: bold;'>")
          .append(booking.getPrice())
          .append(" USD </p></td>");
      CartInfo.append("</tr>");
    }
    try {
      message.setFrom(new InternetAddress("nguyenquan.212002@gmail.com"));
      message.setRecipients(Message.RecipientType.TO, recipientEmail);
      message.setSubject("Test email from Local_Guide");
      message.setText("Đặt lịch thành công");
      htmlTemplate = emailUtils.readFileFromClasspath("templates/html/email.html");
      htmlTemplate = htmlTemplate.replace("[CART_INFO]", CartInfo.toString());
      message.setContent(htmlTemplate, "text/html; charset=utf-8");
    } catch (MessagingException | IOException e) {
      throw new RuntimeException(e);
    }

    mailSender.send(message);

    return true;
  }
}
