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
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<Result> getTourInCartByUser(
      Authentication authentication,
      @RequestParam(value = "isDeleted", required = false, defaultValue = "false")
          boolean isDeleted) {
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
                cartToCartDtoConverter.convert(cart, isDeleted)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Result> deleteBookingInCartByUser(
      Authentication authentication, @PathVariable Long id) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new Result(false, HttpStatus.UNAUTHORIZED.value(), "Let's login"));
    }
    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
    boolean isDeleted = cartService.deleteBookingInCartByIdAndByEmail(customUserDetails.getEmail(), id);
    if (!isDeleted) {
      return ResponseEntity.status(HttpStatus.OK)
          .body(new Result(true, HttpStatus.NOT_FOUND.value(), "Can't delete this booking"));
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Result(true, HttpStatus.OK.value(), "Delete booking in cart successfully"));
  }
}
