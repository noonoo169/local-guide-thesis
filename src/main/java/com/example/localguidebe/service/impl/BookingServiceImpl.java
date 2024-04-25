package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.BookingToBookingDtoConverter;
import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.service.BookingService;
import com.example.localguidebe.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
  private final BookingRepository bookingRepository;
  private final BookingToBookingDtoConverter bookingToBookingDtoConverter;

  private final UserService userService;

  public BookingServiceImpl(
      BookingRepository bookingRepository,
      BookingToBookingDtoConverter bookingToBookingDtoConverter,
      UserService userService) {
    this.bookingRepository = bookingRepository;
    this.bookingToBookingDtoConverter = bookingToBookingDtoConverter;
    this.userService = userService;
  }

  @Override
  public List<BookingDTO> getBookingHistory(String email) {
    Long travelerId = userService.findUserByEmail(email).getId();
    return bookingRepository.getBookingHistory(travelerId).stream()
        .map(bookingToBookingDtoConverter::convert)
        .collect(Collectors.toList());
  }
}
