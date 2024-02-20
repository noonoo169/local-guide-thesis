package com.example.localguidebe.converter;

import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingToBookingDtoConverter {
  private final TourToTourInBookingResponseDtoConverter tourToTourInBookingResponseDtoConverter;

  public BookingToBookingDtoConverter(
      TourToTourInBookingResponseDtoConverter tourToTourInBookingResponseDtoConverter) {
    this.tourToTourInBookingResponseDtoConverter = tourToTourInBookingResponseDtoConverter;
  }

  public BookingDTO convert(Booking source) {
    return new BookingDTO(
        source.getId(),
        source.getStartDate(),
        source.getNumberTravelers(),
        source.getPrice(),
        source.getStatus(),
        tourToTourInBookingResponseDtoConverter.convert(source.getTour()));
  }
}
