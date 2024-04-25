package com.example.localguidebe.converter;

import com.example.localguidebe.dto.CartDTO;
import com.example.localguidebe.entity.Cart;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CartToCartDtoConverter {
  private final BookingToBookingDtoConverter bookingToBookingDtoConverter;

  public CartToCartDtoConverter(BookingToBookingDtoConverter bookingToBookingDtoConverter) {
    this.bookingToBookingDtoConverter = bookingToBookingDtoConverter;
  }

  public CartDTO convert(Cart source) {
    return new CartDTO(
        source.getId(),
        source.getBookings() != null
            ? source.getBookings().stream()
                .map(bookingToBookingDtoConverter::convert)
                .collect(Collectors.toList())
            : null);
  }
}
