package com.example.localguidebe.service;

import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.dto.ProvinceResponseDTO;
import com.example.localguidebe.dto.StatisticalBookingDTO;
import java.util.List;

public interface BookingService {
  List<BookingDTO> getBookingHistory(String email);

  List<ProvinceResponseDTO> findTotalBookingsByCityProvince();

  List<StatisticalBookingDTO> getStatisticalBooking();

  List<BookingDTO> getBookingsOfGuide(String email);
}
