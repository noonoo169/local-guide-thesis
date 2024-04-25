package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.UserToUserDtoConverter;
import com.example.localguidebe.dto.UserDTO;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.WishlistService;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {
  private final TourRepository tourRepository;
  private final UserRepository userRepository;
  private final UserToUserDtoConverter userToUserDtoConverter;

  public WishlistServiceImpl(
      UserRepository userRepository,
      TourRepository tourRepository,
      UserToUserDtoConverter userToUserDtoConverter) {
    this.tourRepository = tourRepository;
    this.userRepository = userRepository;
    this.userToUserDtoConverter = userToUserDtoConverter;
  }

  @Override
  public UserDTO addWishlist(Long tourId, Long travelerId) {
    User traveler = userRepository.findById(travelerId).orElseThrow();
    Tour tour = tourRepository.findById(tourId).orElseThrow();
    traveler.getWishListTour().add(tour);
    tour.getUsers().add(traveler);
    traveler = userRepository.save(traveler);
    return userToUserDtoConverter.convert(traveler);
  }
}
