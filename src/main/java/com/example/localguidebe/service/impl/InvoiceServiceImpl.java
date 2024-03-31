package com.example.localguidebe.service.impl;

import com.example.localguidebe.crypto.etherscan.EtherscanConfig;
import com.example.localguidebe.dto.CryptoPayDto;
import com.example.localguidebe.entity.*;
import com.example.localguidebe.enums.BookingStatusEnum;
import com.example.localguidebe.enums.InvoiceStatus;
import com.example.localguidebe.enums.NotificationTypeEnum;
import com.example.localguidebe.exception.ConvertTourToTourDupeException;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.CryptoPaymentDetailRepository;
import com.example.localguidebe.repository.InvoiceRepository;
import com.example.localguidebe.service.*;
import com.example.localguidebe.system.constants.NotificationMessage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final CartService cartService;
  private final InvoiceRepository invoiceRepository;
  private final NotificationService notificationService;
  private final BusyScheduleService busyScheduleService;
  private final TourDupeService tourDupeService;
  private final EmailService emailService;
  private final CryptoPaymentDetailRepository cryptoPaymentDetailRepository;
  private final BookingRepository bookingRepository;

  @Autowired
  public InvoiceServiceImpl(
      CartService cartService,
      InvoiceRepository invoiceRepository,
      NotificationService notificationService,
      BusyScheduleService busyScheduleService,
      TourDupeService tourDupeService,
      EmailService emailService,
      CryptoPaymentDetailRepository cryptoPaymentDetailRepository,
      BookingRepository bookingRepository) {
    this.cartService = cartService;
    this.invoiceRepository = invoiceRepository;
    this.notificationService = notificationService;
    this.busyScheduleService = busyScheduleService;
    this.tourDupeService = tourDupeService;
    this.emailService = emailService;
    this.cryptoPaymentDetailRepository = cryptoPaymentDetailRepository;
    this.bookingRepository = bookingRepository;
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
    updateBookingAndSendNotification(cart, bookingIds, invoice);
    return invoiceRepository.save(invoice);
  }

  @Override
  public Invoice refundInvoice(Invoice invoice, Double refundVndPrice) {
    User traveler = invoice.getTraveler();
    invoice.setRefundVndPrice(refundVndPrice);
    invoice.setStatus(InvoiceStatus.REFUNDED);
    invoice
        .getBookings()
        .forEach(
            booking -> {
              busyScheduleService.updateBusyScheduleBeforeUpdateOrDeleteBooking(null, booking);

              // notification send to guide
              User guide = booking.getTour().getGuide();
              notificationService
                  .sendNotificationForNewBookingOrRefundBookingOrReviewOnGuideOrReviewOnTourOrNewTravelerRequestToGuide(
                      guide,
                      traveler,
                      booking.getId(),
                      NotificationTypeEnum.CANCEL_BOOKING,
                      NotificationMessage.CANCEL_BOOKING + traveler.getFullName());
            });
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

  @Transactional
  @Override
  public Invoice createInvoiceByCrypto(CryptoPayDto cryptoPayDto) {
    Cart cart = cartService.getCartByEmail(cryptoPayDto.travelerEmail());

    if (cart == null) return null;
    CryptoPaymentDetail cryptoPaymentDetail =
        cryptoPaymentDetailRepository.save(
            CryptoPaymentDetail.builder()
                .tx(cryptoPayDto.txHash())
                .sepoliaEthPrice(cryptoPayDto.sepoliaEthPrice())
                .usdRate(cryptoPayDto.usdRate())
                .senderAddress(cryptoPayDto.senderAddress())
                .recipientAddress(EtherscanConfig.recipientAddress)
                .build());

    Invoice invoice =
        Invoice.builder()
            .priceTotal(cryptoPayDto.priceTotal())
            .createAt(LocalDateTime.now())
            .fullName(cryptoPayDto.fullName())
            .phone(cryptoPayDto.phone())
            .email(cryptoPayDto.email())
            .traveler(cart.getTraveler())
            .status(InvoiceStatus.PAID)
            .cryptoPaymentDetail(cryptoPaymentDetail)
            .build();
    updateBookingAndSendNotification(cart, cryptoPayDto.bookingIds(), invoice);
    return invoiceRepository.save(invoice);
  }

  @Override
  public void updateBookingAndSendNotification(Cart cart, List<Long> bookingIds, Invoice invoice) {
    User traveler = cart.getTraveler();
    List<Booking> bookings = new ArrayList<>();
    bookingIds.forEach(
        bookingId -> {
          Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
          if (optionalBooking.isEmpty()) throw new RuntimeException();
          Booking booking = optionalBooking.get();
          bookings.add(booking);
          // add tour dupe
          try {
            booking.setTourDupe(tourDupeService.getTourDupe(booking.getTour()));
          } catch (Exception e) {
            throw new ConvertTourToTourDupeException(e.getMessage());
          }
          booking.setInvoice(invoice);
          booking.setStatus(BookingStatusEnum.PAID);

          // notification send to guide
          User guide = booking.getTour().getGuide();
          notificationService
              .sendNotificationForNewBookingOrRefundBookingOrReviewOnGuideOrReviewOnTourOrNewTravelerRequestToGuide(
                  guide,
                  traveler,
                  bookingId,
                  NotificationTypeEnum.RECEIVED_BOOKING,
                  NotificationMessage.RECEIVED_BOOKING + traveler.getFullName());
        });
    // create notification for traveler
    Notification travelerNotification =
        notificationService.addNotification(
            traveler.getId(),
            null,
            invoice.getId(),
            NotificationTypeEnum.BOOKED_TOUR,
            NotificationMessage.BOOKED_TOUR);
    invoice.setBookings(bookings);
  }
}
