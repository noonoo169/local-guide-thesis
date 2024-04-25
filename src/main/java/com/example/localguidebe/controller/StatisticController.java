package com.example.localguidebe.controller;

import com.example.localguidebe.service.StatisticService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
  private final StatisticService statisticService;

  public StatisticController(StatisticService statisticService) {
    this.statisticService = statisticService;
  }

  @GetMapping("/guides")
  public ResponseEntity<Result> getRevenueByGuide(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer limit,
      @RequestParam(required = false, defaultValue = "desc") String order) {
    try {
      return new ResponseEntity<>(
          new Result(
              false,
              HttpStatus.OK.value(),
              "get statistic of guide successfully",
              statisticService.getStatisticalByGuide(page, limit, order)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "Get statistics of failed guides", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/tours")
  public ResponseEntity<Result> getRevenueByTour(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "5") Integer limit,
      @RequestParam(required = false, defaultValue = "desc") String order) {
    try {
      return new ResponseEntity<>(
          new Result(
              false,
              HttpStatus.OK.value(),
              "get statistic of tour successfully",
              statisticService.getStatisticalByTour(page, limit, order)),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "Get statistics of failed tours", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/tour/{TourId}")
  public ResponseEntity<Result> getRevenueByPerTour(
      @PathVariable("TourId") Long tourId, Authentication authentication) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            return new ResponseEntity<>(
                new Result(
                    false,
                    HttpStatus.OK.value(),
                    "get statistic of per tour successfully",
                    statisticService.getStatisticByPerTour(tourId)),
                HttpStatus.OK);
          } catch (Exception e) {
            return new ResponseEntity<>(
                new Result(
                    false, HttpStatus.CONFLICT.value(), "Get statistics of failed per tour", null),
                HttpStatus.CONFLICT);
          }
        });
  }
}
