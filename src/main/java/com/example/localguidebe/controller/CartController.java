package com.example.localguidebe.controller;

import com.example.localguidebe.converter.CartToCartDtoConverter;
import com.example.localguidebe.entity.Cart;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.CartService;
import com.example.localguidebe.system.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {
  private final CartService cartService;
  private final CartToCartDtoConverter cartToCartDtoConverter;

  @Autowired
  public CartController(CartService cartService, CartToCartDtoConverter cartToCartDtoConverter) {
    this.cartService = cartService;
    this.cartToCartDtoConverter = cartToCartDtoConverter;
  }

  @GetMapping("")
  public ResponseEntity<Result> getTourInCartByUser(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new Result(false, HttpStatus.UNAUTHORIZED.value(), "Let's login"));
    }
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    Cart cart = cartService.getCartByEmail(customUserDetails.getEmail());
    if (cart == null) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              new Result(
                  true,
                  HttpStatus.NOT_FOUND.value(),
                  "You haven't added any tours to the cart yet."));
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Result(
                true,
                HttpStatus.OK.value(),
                "Get cart successfully",
                cartToCartDtoConverter.convert(cart)));
  }
}
