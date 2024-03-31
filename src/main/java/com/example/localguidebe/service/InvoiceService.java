package com.example.localguidebe.service;

import com.example.localguidebe.dto.CryptoPayDto;
import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.entity.Invoice;
import java.util.List;
import java.util.Optional;

public interface InvoiceService {
  Invoice createBookingInInvoice(
      String vnp_TxnRef,
      List<Long> bookingIds,
      String email,
      String travelerEmail,
      String fullName,
      String phone,
      Double priceTotal,
      Double priceInVND,
      Double usdVndRate);

  Invoice refundInvoice(Invoice invoice, Double refundVndPrice);

  Optional<Invoice> findById(Long id);

  Double getRefundAmount(Invoice invoice);

  Invoice createInvoiceByCrypto(CryptoPayDto cryptoPayDto);

  void updateBookingAndSendNotification(Cart cart, List<Long> bookingIds, Invoice invoice);
}
