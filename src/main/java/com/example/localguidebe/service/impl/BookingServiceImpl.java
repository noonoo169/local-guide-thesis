package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.BookingToBookingDtoConverter;
import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.dto.ProvinceResponseDTO;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.service.BookingService;
import com.example.localguidebe.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
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

  @Override
  public List<ProvinceResponseDTO> FindForSuggestedTours() {
    List<ProvinceResponseDTO> provinceResponseDTOS = new ArrayList<>();
    List<Object> suggestedTours =
        bookingRepository.FindForSuggestedTours().stream()
            .flatMap(Arrays::stream)
            .collect(Collectors.toList());
    for (int count = 0; count < suggestedTours.size() - 1; count += 2) {
      String firstElement = suggestedTours.get(count).toString();
      Long secondElement = (Long) suggestedTours.get(count + 1);
      ProvinceResponseDTO provinceResponseDTO =
          new ProvinceResponseDTO(firstElement, secondElement);
      provinceResponseDTOS.add(provinceResponseDTO);
    }
    return provinceResponseDTOS;
  }
}
