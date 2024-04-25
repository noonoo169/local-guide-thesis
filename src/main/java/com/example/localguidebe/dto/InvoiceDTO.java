package com.example.localguidebe.dto;

import java.time.LocalDateTime;
import java.util.List;

public record InvoiceDTO(
    Long id,
    Double priceTotal,
    LocalDateTime createAt,
    Double vndPrice,
    Double conversionRate,
    List<BookingDTO> tours) {}
