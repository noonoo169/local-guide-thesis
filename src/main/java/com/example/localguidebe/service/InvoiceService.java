package com.example.localguidebe.service;

import com.example.localguidebe.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface InvoiceService {
    Invoice createBookingInInvoice(List<Long> bookingIds, String email, Double priceTotal);

    Optional<Invoice> findById(Long id);
}
