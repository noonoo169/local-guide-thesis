package com.example.localguidebe.service;

import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.ReviewRequestDTO;
import com.example.localguidebe.entity.Review;
import com.example.localguidebe.entity.User;

public interface ReviewService {
  Review addReviewForGuide(ReviewRequestDTO reviewRequestDTO, Long guideId, User traveler);
  TourDTO addReviewForTour(ReviewRequestDTO reviewRequestDTO, Long tourId, String email);
}
