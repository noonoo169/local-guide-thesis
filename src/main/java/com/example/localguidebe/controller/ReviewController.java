package com.example.localguidebe.controller;

import com.example.localguidebe.converter.ReviewToReviewDtoConverter;
import com.example.localguidebe.dto.requestdto.ReviewRequestDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.ReviewService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
  private final ReviewService reviewService;
  private final UserService userService;
  private final ReviewToReviewDtoConverter reviewToReviewDtoConverter;

  public ReviewController(
      ReviewService reviewService,
      UserService userService,
      ReviewToReviewDtoConverter reviewToReviewDtoConverter) {
    this.reviewService = reviewService;
    this.userService = userService;
    this.reviewToReviewDtoConverter = reviewToReviewDtoConverter;
  }

  @PostMapping("guide-reviews/{guideId}")
  public ResponseEntity<Result> addReviewForGuide(
      Authentication authentication,
      @RequestBody ReviewRequestDTO reviewRequestDTO,
      @PathVariable("guideId") Long guideId) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          String travelerEmail = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
          User traveler = userService.findUserByEmail(travelerEmail);
          if (!userService.isTravelerCanAddReviewForGuide(traveler, guideId)) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        false, HttpStatus.OK.value(), "You can't not add review for this guide"));
          }
          return ResponseEntity.status(HttpStatus.OK)
              .body(
                  new Result(
                      true,
                      HttpStatus.OK.value(),
                      "Thank you for giving feedback.",
                      reviewToReviewDtoConverter.convert(
                          reviewService.addReviewForGuide(reviewRequestDTO, guideId, traveler))));
        });
  }
}
