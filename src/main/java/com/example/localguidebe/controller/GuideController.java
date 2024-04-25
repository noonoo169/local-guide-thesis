package com.example.localguidebe.controller;

import com.example.localguidebe.converter.UserToGuideDtoConverter;
import com.example.localguidebe.dto.responsedto.SearchGuideDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guides")
public class GuideController {

  private final UserService userService;
  private final UserToGuideDtoConverter userToGuideDtoConverter;

  @Autowired
  public GuideController(UserService userService, UserToGuideDtoConverter userToGuideDtoConverter) {
    this.userService = userService;
    this.userToGuideDtoConverter = userToGuideDtoConverter;
  }

  @GetMapping("/search")
  public ResponseEntity<Result> search(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer limit,
      @RequestParam(required = false, defaultValue = "overallRating") String sortBy,
      @RequestParam(required = false, defaultValue = "desc") String order,
      @RequestParam(required = false, defaultValue = "") Double ratingFilter,
      @RequestParam(required = false, defaultValue = "") String searchValue) {
    Page<User> guides =
        userService.getGuides(page, limit, sortBy, order, ratingFilter, searchValue);

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Result(
                true,
                HttpStatus.OK.value(),
                "All guide",
                new SearchGuideDTO(
                    guides.stream().map(userToGuideDtoConverter::convert).toList(),
                    guides.getTotalPages(),
                    guides.getTotalElements())));
  }
}
