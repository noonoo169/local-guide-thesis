package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.NotificationToNotificationDtoConverter;
import com.example.localguidebe.entity.*;
import com.example.localguidebe.enums.NotificationTypeEnum;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.InvoiceRepository;
import com.example.localguidebe.service.CartService;
import com.example.localguidebe.service.InvoiceService;
import com.example.localguidebe.service.NotificationService;
import com.example.localguidebe.system.NotificationMessage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final CartService cartService;
  private final BookingRepository bookingRepository;
  private final InvoiceRepository invoiceRepository;
  private final NotificationService notificationService;
  private final SimpMessagingTemplate messagingTemplate;
  private final NotificationToNotificationDtoConverter notificationToNotificationDtoConverter;

  @Autowired
  public InvoiceServiceImpl(
      CartService cartService,
      BookingRepository bookingRepository,
      InvoiceRepository invoiceRepository,
      NotificationService notificationService,
      SimpMessagingTemplate messagingTemplate,
      NotificationToNotificationDtoConverter notificationToNotificationDtoConverter) {
    this.cartService = cartService;
    this.bookingRepository = bookingRepository;
    this.invoiceRepository = invoiceRepository;
    this.notificationService = notificationService;
    this.messagingTemplate = messagingTemplate;
    this.notificationToNotificationDtoConverter = notificationToNotificationDtoConverter;
  }

  @Override
  @Transactional
  public Invoice createBookingInInvoice(
      List<Long> bookingIds,
      String email,
      Double priceTotal,
      Double priceInVND,
      Double usdVndRate) {
    Cart cart = cartService.getCartByEmail(email);

    if (cart == null) return null;
    List<Booking> bookings =
        new ArrayList<>(
            cart.getBookings().stream().filter(booking -> !booking.isDeleted()).toList());
    Invoice invoice =
        Invoice.builder()
            .priceTotal(priceTotal)
            .vndPrice(priceInVND)
            .conversionRate(usdVndRate)
            .createAt(LocalDateTime.now())
            .traveler(cart.getTraveler())
            .build();
    bookings.forEach(
        booking -> {
          booking.setInvoice(invoice);
          // notification send to guide
          Notification guideNotification =
              notificationService.addNotification(
                  booking.getTour().getGuide().getId(),
                  cart.getTraveler().getId(),
                  booking.getId(),
                  NotificationTypeEnum.RECEIVED_BOOKING,
                  NotificationMessage.RECEIVED_BOOKING + cart.getTraveler().getFullName());

          messagingTemplate.convertAndSend(
              "/topic/" + booking.getTour().getGuide().getEmail(),
              notificationToNotificationDtoConverter.convert(guideNotification));

          // notification send to traveler
          Notification travelerNotification =
              notificationService.addNotification(
                  cart.getTraveler().getId(),
                  null,
                  booking.getId(),
                  NotificationTypeEnum.BOOKED_TOUR,
                  NotificationMessage.BOOKED_TOUR);

          messagingTemplate.convertAndSend(
              "/topic/" + cart.getTraveler().getEmail(),
              notificationToNotificationDtoConverter.convert(travelerNotification));
        });
    invoice.setBookings(bookings);
    bookingIds.forEach(bookingRepository::setBookingStatusToPaid);
    return invoiceRepository.save(invoice);
  }

  @Override
  public Optional<Invoice> findById(Long id) {
    return invoiceRepository.findById(id);
  }
}
