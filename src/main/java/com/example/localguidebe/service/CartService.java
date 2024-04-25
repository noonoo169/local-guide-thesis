package com.example.localguidebe.service;

import com.example.localguidebe.entity.Cart;

public interface CartService {
  Cart getCartByEmail(String email);

  boolean deleteBookingInCartByIdAndByEmail(String email, Long bookingId);
}
