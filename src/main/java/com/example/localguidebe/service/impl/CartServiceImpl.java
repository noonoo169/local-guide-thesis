package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.requestdto.UpdateBookingDTO;
import com.example.localguidebe.entity.Booking;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.repository.BookingRepository;
import com.example.localguidebe.repository.CartRepository;
import com.example.localguidebe.service.CartService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;
  private final BookingRepository bookingRepository;

  @Autowired
  public CartServiceImpl(CartRepository cartRepository, BookingRepository bookingRepository) {
    this.cartRepository = cartRepository;
    this.bookingRepository = bookingRepository;
  }

  @Override
  public Cart getCartByEmail(String email) {
    Optional<Cart> optionalCart = cartRepository.getCartByEmail(email);
    return optionalCart.orElse(null);
  }

  @Override
  public boolean deleteBookingInCartByIdAndByEmail(String email, Long bookingId) {
    Cart cart = getCartByEmail(email);
    if (cart == null) {
      return false;
    }
    boolean bookingDeleteExist =
        cart.getBookings().stream().anyMatch(booking -> booking.getId().equals(bookingId));
    if (!bookingDeleteExist) {
      return false;
    }
    bookingRepository.deleteById(bookingId);
    return true;
  }

  @Transactional
  @Override
  public Cart updateBookingInCart(String email, UpdateBookingDTO updateBookingDTO) {
    Cart cart = getCartByEmail(email);
    if (cart == null) return null;

    Optional<Booking> optionalBooking =
        cart.getBookings().stream()
            .filter(booking -> booking.getId().equals(updateBookingDTO.id()))
            .findFirst();
    if (optionalBooking.isEmpty()) return null;

    Booking booking = optionalBooking.get();
    booking.setNumberTravelers(updateBookingDTO.numberTravelers());
    // TODO: create API to check available start date
    booking.setStartDate(updateBookingDTO.startDate());
    bookingRepository.save(booking);
    return cart;
  }
}
