package com.example.localguidebe.controller;

import com.example.localguidebe.converter.TourToTourDtoConverter;
import com.example.localguidebe.converter.TourToUpdateTourResponseDtoConverter;
import com.example.localguidebe.dto.CategoryDTO;
import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.CategoryService;
import com.example.localguidebe.service.TourService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AddressUtils;
import com.example.localguidebe.utils.AuthUtils;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tours")
@CrossOrigin("*")
public class TourController {

  private TourService tourService;
  private CategoryService categoryService;
  private TourToTourDtoConverter tourToTourDtoConverter;
  private final TourToUpdateTourResponseDtoConverter tourToUpdateTourResponseDtoConverter;
  private final UserService userService;

  public TourController(
      TourToUpdateTourResponseDtoConverter tourToUpdateTourResponseDtoConverter,
      UserService userService) {
    this.tourToUpdateTourResponseDtoConverter = tourToUpdateTourResponseDtoConverter;
    this.userService = userService;
  }

  @Autowired
  public void setTourToDtoConverter(TourToTourDtoConverter tourToTourDtoConverter) {
    this.tourToTourDtoConverter = tourToTourDtoConverter;
  }

  @Autowired
  public void setTourService(TourService tourService) {
    this.tourService = tourService;
  }

  @Autowired
  public void setCategoryService(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @PostMapping("")
  public ResponseEntity<Result> addTour(
      Authentication authentication, @RequestBody TourRequestDTO tourRequestDTO) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            return new ResponseEntity<>(
                new Result(
                    true,
                    HttpStatus.OK.value(),
                    "tour added successfully",
                    tourToTourDtoConverter.convert(
                        tourService.saveTour(
                            tourRequestDTO,
                            ((CustomUserDetails) authentication.getPrincipal()).getEmail()))),
                HttpStatus.OK);
          } catch (Exception e) {
            return new ResponseEntity<>(
                new Result(false, HttpStatus.CONFLICT.value(), "Adding tour failed", null),
                HttpStatus.CONFLICT);
          }
        });
  }

  @GetMapping("/{id}")
  public ResponseEntity<Result> getTour(@PathVariable("id") Long id) {
    try {
      return new ResponseEntity<>(
          new Result(
              true,
              HttpStatus.OK.value(),
              "Get the tour successfully",
              tourService.getTourById(id)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "No tour found", null),
          HttpStatus.CONFLICT);
    }
  }

  @PutMapping("")
  public ResponseEntity<Result> update(
      Authentication authentication, @RequestBody UpdateTourRequestDTO updateTourRequestDTO) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            User user =
                userService.findUserByEmail(
                    ((CustomUserDetails) authentication.getPrincipal()).getEmail());
            if (user.getTours().stream()
                .noneMatch(tour -> tour.getId().equals(updateTourRequestDTO.id()))) {
              return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                  .body(
                      new Result(
                          false,
                          HttpStatus.NOT_ACCEPTABLE.value(),
                          "You can't not update this tour"));
            }
            Tour tour = tourService.updateTour(updateTourRequestDTO);
            if (tour == null) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body(
                      new Result(
                          false,
                          HttpStatus.INTERNAL_SERVER_ERROR.value(),
                          "Update tour information failed"));
            }
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        true,
                        HttpStatus.OK.value(),
                        "Update tour information successfully",
                        tourToUpdateTourResponseDtoConverter.convert(tour)));
          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Result(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
          }
        });
  }

  @GetMapping("")
  public ResponseEntity<Result> getListTour() {
    try {
      return new ResponseEntity<>(
          new Result(
              true, HttpStatus.OK.value(), "Get the list successfully", tourService.getListTour()),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "get the failure list", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/search")
  public ResponseEntity<Result> searchTour(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer limit,
      @RequestParam(required = false, defaultValue = "overallRating") String sortBy,
      @RequestParam(required = false, defaultValue = "desc") String order,
      @RequestParam(required = false, defaultValue = "") String searchValue,
      @RequestParam(required = false, defaultValue = "0.0") Double minPrice,
      @RequestParam(required = false, defaultValue = "10000000") Double maxPrice,
      @RequestParam(required = false, defaultValue = "") List<Long> categoryId) {
    if (categoryId.size() == 0) {
      categoryId.addAll(categoryService.getCategories().stream().map(CategoryDTO::getId).toList());
    }

    try {
      return new ResponseEntity<>(
          new Result(
              true,
              HttpStatus.OK.value(),
              "Get the tour successfully",
              tourService.getTours(
                  page,
                  limit,
                  sortBy,
                  order,
                  AddressUtils.removeVietnameseAccents(searchValue),
                  minPrice,
                  maxPrice,
                  categoryId)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "No tour found", null),
          HttpStatus.CONFLICT);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Result> deleteTour(@PathVariable("id") Long id) {

    try {
      return new ResponseEntity<>(
          new Result(
              false,
              HttpStatus.OK.value(),
              "Deleted tour successfully",
              tourService.deleteTour(id)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "Tour deletion failed", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/guide/{id}")
  public ResponseEntity<Result> getToursOfGuide(@PathVariable("id") Long guideId) {
    try {
      return new ResponseEntity<>(
          new Result(
              true,
              HttpStatus.OK.value(),
              "Get the list successfully",
              tourService.getToursOfGuide(guideId).stream().map(tourToTourDtoConverter::convert)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "Get the failure list", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/{id}/tour-start-time-available")
  public ResponseEntity<Result> getTourStartTimeAvailable(
      @PathVariable Long id, @RequestParam() LocalDate localDate) {
    return new ResponseEntity<>(
        new Result(
            true,
            HttpStatus.OK.value(),
            "Get the list successfully",
            tourService.getTourStartTimeAvailable(id, localDate)),
        HttpStatus.OK);
  }

  @GetMapping("/filter/{tourId}")
  public ResponseEntity<Result> getTourByFilter(
      @PathVariable Long tourId,
      @RequestParam(required = false, defaultValue = "") List<Integer> ratings,
      @RequestParam(required = false, defaultValue = "Most recent") String sortBy) {
    try {
      return new ResponseEntity<>(
          new Result(
              true,
              HttpStatus.OK.value(),
              "filter the comment list for tour successfully",
              tourService.filterReviewForTour(ratings, tourId, sortBy)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(
              false, HttpStatus.CONFLICT.value(), "filter the failure comment list for tour", null),
          HttpStatus.CONFLICT);
    }
  }
}
