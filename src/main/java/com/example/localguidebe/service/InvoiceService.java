package com.example.localguidebe.service;

import com.example.localguidebe.entity.Invoice;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
  Invoice createBookingInInvoice(
      List<Long> bookingIds,
      String email,
      String travelerEmail,
      String fullName,
      String phone,
      Double priceTotal,
      Double priceInVND,
      Double usdVndRate);

  Optional<Invoice> findById(Long id);
}
