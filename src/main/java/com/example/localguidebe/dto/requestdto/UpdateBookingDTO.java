package com.example.localguidebe.dto.requestdto;

import java.time.LocalDateTime;

public record UpdateBookingDTO(Long id, Integer numberTravelers, LocalDateTime startDate) {}
