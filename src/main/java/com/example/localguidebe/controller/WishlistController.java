package com.example.localguidebe.controller;

import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.WishlistService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {
  private final WishlistService wishlistService;

  public WishlistController(WishlistService wishlistService) {
    this.wishlistService = wishlistService;
  }

  @PostMapping("/{tourId}")
  public ResponseEntity<Result> addWishlistByTraveler(
      Authentication authentication, @PathVariable("tourId") Long tourId) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            return new ResponseEntity<>(
                new Result(
                    false,
                    HttpStatus.OK.value(),
                    "add wishlist by traveler successfully",
                    wishlistService.addWishlist(
                        tourId, ((CustomUserDetails) authentication.getPrincipal()).getId())),
                HttpStatus.OK);
          } catch (Exception e) {
            return new ResponseEntity<>(
                new Result(
                    false,
                    HttpStatus.CONFLICT.value(),
                    "add the failure wishlist by traveler",
                    null),
                HttpStatus.CONFLICT);
          }
        });
  }
}
