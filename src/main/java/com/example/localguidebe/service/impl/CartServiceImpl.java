package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.repository.CartRepository;
import com.example.localguidebe.service.CartService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
  private final CartRepository cartRepository;

  @Autowired
  public CartServiceImpl(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  @Override
  public Cart getCartByEmail(String email) {
    Optional<Cart> optionalCart = cartRepository.getCartByEmail(email);
    return optionalCart.orElse(null);
  }
}
