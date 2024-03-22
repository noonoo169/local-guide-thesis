package com.example.localguidebe.service;

import com.example.localguidebe.dto.UserDTO;

public interface WishlistService {
  UserDTO addWishlist(Long tourId, Long travelerId);
}
