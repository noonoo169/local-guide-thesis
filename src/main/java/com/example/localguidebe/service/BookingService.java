package com.example.localguidebe.service;

import com.example.localguidebe.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    List<BookingDTO> getBookingHistory(String email);

}
