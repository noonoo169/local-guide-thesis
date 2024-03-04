package com.example.localguidebe.converter;

import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.entity.Booking;
import org.springframework.stereotype.Component;

@Component
public class BookingToBookingDtoConverter {
  private final TourToTourInBookingResponseDtoConverter tourToTourInBookingResponseDtoConverter;
  private final UserToGuideDtoConverter userToGuideDtoConverter;

  public BookingToBookingDtoConverter(
      TourToTourInBookingResponseDtoConverter tourToTourInBookingResponseDtoConverter,
      UserToGuideDtoConverter userToGuideDtoConverter) {
    this.tourToTourInBookingResponseDtoConverter = tourToTourInBookingResponseDtoConverter;
    this.userToGuideDtoConverter = userToGuideDtoConverter;
  }

  public BookingDTO convert(Booking source) {
    return new BookingDTO(
        source.getId(),
        source.getStartDate(),
        source.getNumberTravelers(),
        source.getPrice(),
        source.getStatus(),
        tourToTourInBookingResponseDtoConverter.convert(source.getTour()),
        userToGuideDtoConverter.convert(source.getTour().getGuide()));
  }
}
