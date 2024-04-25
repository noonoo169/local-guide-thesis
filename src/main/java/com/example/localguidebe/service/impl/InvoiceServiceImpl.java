package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.NotificationToNotificationDtoConverter;
import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.entity.*;
import com.example.localguidebe.enums.InvoiceStatus;
import com.example.localguidebe.enums.NotificationTypeEnum;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.InvoiceRepository;
import com.example.localguidebe.service.BusyScheduleService;
import com.example.localguidebe.service.CartService;
import com.example.localguidebe.service.InvoiceService;
import com.example.localguidebe.service.NotificationService;
import com.example.localguidebe.system.NotificationMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private final TourToTourDtoConverter tourToTourDtoConverter;
  private final BusyScheduleService busyScheduleService;

  Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

  @Autowired
  public InvoiceServiceImpl(
      CartService cartService,
      BookingRepository bookingRepository,
      InvoiceRepository invoiceRepository,
      NotificationService notificationService,
      SimpMessagingTemplate messagingTemplate,
      NotificationToNotificationDtoConverter notificationToNotificationDtoConverter,
      TourToTourDtoConverter tourToTourDtoConverter,
      BusyScheduleService busyScheduleService) {
    this.cartService = cartService;
    this.bookingRepository = bookingRepository;
    this.invoiceRepository = invoiceRepository;
    this.notificationService = notificationService;
    this.messagingTemplate = messagingTemplate;
    this.notificationToNotificationDtoConverter = notificationToNotificationDtoConverter;
    this.tourToTourDtoConverter = tourToTourDtoConverter;
    this.busyScheduleService = busyScheduleService;
  }

  @Override
  @Transactional
  public Invoice createBookingInInvoice(
      String vnp_TxnRef,
      List<Long> bookingIds,
      String email,
      String travelerEmail,
      String fullName,
      String phone,
      Double priceTotal,
      Double priceInVND,
      Double usdVndRate) {
    Cart cart = cartService.getCartByEmail(travelerEmail);

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
            .fullName(fullName)
            .phone(phone)
            .email(email)
            .traveler(cart.getTraveler())
            .vnpTxnRef(vnp_TxnRef)
            .status(InvoiceStatus.PAID)
            .build();
    bookings.forEach(
        booking -> {
          // add tour dupe
          try {
            TourDupe tourDupe = new TourDupe(tourToTourDtoConverter.convert(booking.getTour()));
            booking.setTourDupe(tourDupe);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }

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
  public Invoice refundInvoice(Invoice invoice, Double refundVndPrice) {
    invoice.setRefundVndPrice(refundVndPrice);
    invoice.setStatus(InvoiceStatus.REFUNDED);
    invoice
        .getBookings()
        .forEach(
            booking ->
                busyScheduleService.updateBusyScheduleBeforeUpdateOrDeleteBooking(null, booking));
    return invoiceRepository.save(invoice);
  }

  @Override
  public Optional<Invoice> findById(Long id) {
    return invoiceRepository.findById(id);
  }

  @Override
  public Double getRefundAmount(Invoice invoice) {
    LocalDateTime now = LocalDateTime.now();
    if (Duration.between(invoice.getCreateAt(), now).toMinutes() < 59) return invoice.getVndPrice();

    LocalDateTime minStartDateOfBooking =
        invoice.getBookings().stream()
            .map(Booking::getStartDate)
            .min(LocalDateTime::compareTo)
            .orElse(null);
    if (minStartDateOfBooking == null) return 0.0;

    long daysBetween =
        ChronoUnit.DAYS.between(now.toLocalDate(), minStartDateOfBooking.toLocalDate());
    if (daysBetween > 7) return invoice.getVndPrice();
    else if (daysBetween > 0) return invoice.getVndPrice() / 2;
    else return 0.0;
  }
}
