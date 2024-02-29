package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.entity.Invoice;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.InvoiceRepository;
import com.example.localguidebe.service.CartService;
import com.example.localguidebe.service.InvoiceService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvoiceServiceImpl implements InvoiceService {
  private final CartService cartService;
  private final BookingRepository bookingRepository;
  private final InvoiceRepository invoiceRepository;

  @Autowired
  public InvoiceServiceImpl(
      CartService cartService,
      BookingRepository bookingRepository,
      InvoiceRepository invoiceRepository) {
    this.cartService = cartService;
    this.bookingRepository = bookingRepository;
    this.invoiceRepository = invoiceRepository;
  }

  @Override
  @Transactional
  public Invoice createBookingInInvoice(List<Long> bookingIds, String email, Double priceTotal) {
    bookingIds.forEach(bookingRepository::setBookingStatusToPaid);
    Cart cart = cartService.getCartByEmail(email);
    if (cart == null) return null;
    List<Booking> bookings = new ArrayList<>(cart.getBookings().stream().filter(booking -> !booking.isDeleted()).toList());
    Invoice invoice =
        Invoice.builder()
            .priceTotal(priceTotal)
            .createAt(LocalDateTime.now())
            .traveler(cart.getTraveler())
            .build();
    bookings.forEach(
        booking -> {
          booking.setInvoice(invoice);
        });
    invoice.setBookings(bookings);
    return invoiceRepository.save(invoice);
  }

  @Override
  public Optional<Invoice> findById(Long id) {
    return invoiceRepository.findById(id);
  }
}
