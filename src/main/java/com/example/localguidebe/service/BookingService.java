package com.example.localguidebe.service;

import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.dto.ProvinceResponseDTO;

import java.util.List;

public interface BookingService {
    List<BookingDTO> getBookingHistory(String email);
    List<ProvinceResponseDTO> FindForSuggestedTours();

}
