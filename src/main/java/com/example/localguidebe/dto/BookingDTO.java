package com.example.localguidebe.dto;

import com.example.localguidebe.dto.responsedto.TourInBookingResponseDTO;
import com.example.localguidebe.enums.BookingStatusEnum;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public record BookingDTO(
    Long id,
    LocalDateTime startDate,
    Integer numberTraveler,
    Double price,
    BookingStatusEnum status,
    TourInBookingResponseDTO tour) {}
