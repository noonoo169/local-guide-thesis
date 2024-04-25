package com.example.localguidebe.service.impl;

import com.example.localguidebe.converter.ReviewToReviewResponseDto;
import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.ReviewRequestDTO;
import com.example.localguidebe.dto.responsedto.ReviewResponseDTO;
import com.example.localguidebe.entity.Review;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.ReviewRepository;
import com.example.localguidebe.repository.TourRepository;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.ReviewService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ReviewServiceImpl implements ReviewService {
  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;

  private final TourRepository tourRepository;
  private TourToTourDtoConverter tourToTourDtoConverter;

  private ReviewToReviewResponseDto reviewToReviewResponseDto;

  public ReviewServiceImpl(
      ReviewRepository reviewRepository,
      UserRepository userRepository,
      TourRepository tourRepository,
      TourToTourDtoConverter tourToTourDtoConverter,
      ReviewToReviewResponseDto reviewToReviewResponseDto) {
    this.reviewRepository = reviewRepository;
    this.userRepository = userRepository;
    this.tourRepository = tourRepository;
    this.tourToTourDtoConverter = tourToTourDtoConverter;
    this.reviewToReviewResponseDto = reviewToReviewResponseDto;
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

  @Override
  public TourDTO addReviewForTour(ReviewRequestDTO reviewRequestDTO, Long tourId, String email) {
    User traveler = userRepository.findUserByEmail(email);

    Tour tour = tourRepository.findById(tourId).orElseThrow();

    Review newReview =
        Review.builder()
            .tour(tourRepository.findById(tourId).orElseThrow())
            .comment(reviewRequestDTO.comment())
            .rating(reviewRequestDTO.rating())
            .createdAt(LocalDateTime.now())
            .traveler(traveler)
            .build();
    tour.getReviews().add(newReview);
    tour.setOverallRating(
        tour.getReviews().stream()
            .filter(review -> review.getRating() > 0)
            .map(Review::getRating)
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0));

    return tourToTourDtoConverter.convert(tourRepository.save(tour));
  }

  @Override
  public List<ReviewResponseDTO> getReviewForTour(Long tourId) {
    Tour tour = tourRepository.findById(tourId).orElseThrow();
    return tour.getReviews().stream()
        .map(reviewToReviewResponseDto::convert)
        .collect(Collectors.toList());
  }

  public List<Review> getReviewsOfGuide(Long guideId) {
    return reviewRepository.getReviewsByGuideId(guideId);
  }
}
