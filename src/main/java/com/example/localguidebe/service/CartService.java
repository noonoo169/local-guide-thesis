package com.example.localguidebe.service;

import com.example.localguidebe.dto.BookingDTO;
import com.example.localguidebe.dto.CartDTO;
import com.example.localguidebe.dto.requestdto.AddBookingRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateBookingDTO;
import com.example.localguidebe.entity.Cart;

import java.util.List;

public interface CartService {
  Cart getCartByEmail(String email);

  boolean deleteBookingInCartByIdAndByEmail(String email, Long bookingId);

    Cart updateBookingInCart(String email, UpdateBookingDTO updateBookingDTO);
  CartDTO addBookingInCart(String email, AddBookingRequestDTO bookingDTO);
}
