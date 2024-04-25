package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.requestdto.ReviewRequestDTO;
import com.example.localguidebe.entity.Review;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.ReviewRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.ReviewService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository) {
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Review addReviewForGuide(ReviewRequestDTO reviewRequestDTO, Long guideId, User traveler) {
    User guide = userRepository.findById(guideId).orElse(null);
    Review review =
        Review.builder()
            .comment(reviewRequestDTO.comment())
            .rating(reviewRequestDTO.rating())
            .guide(guide)
            .traveler(traveler)
            .createdAt(LocalDateTime.now())
            .build();
    return reviewRepository.save(review);
  }
}
