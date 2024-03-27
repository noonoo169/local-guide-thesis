package com.example.localguidebe.controller;

import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.StatisticService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping("/guides")
    public ResponseEntity<Result> getStatisticByGuides() {
        try {
            return new ResponseEntity<>(
                    new Result(
                            false,
                            HttpStatus.OK.value(),
                            "get statistic of guide successfully",
                            statisticService.getStatisticalByGuide()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Result(false, HttpStatus.CONFLICT.value(), "Get statistics of failed guides", null),
                    HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/tours")
    public ResponseEntity<Result> getStatisticByTours() {
        try {
            return new ResponseEntity<>(
                    new Result(
                            false,
                            HttpStatus.OK.value(),
                            "get statistic of tour successfully",
                            statisticService.getStatisticalByTour()),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new Result(false, HttpStatus.CONFLICT.value(), "Get statistics of failed tours", null),
                    HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/tour/{TourId}")
    public ResponseEntity<Result> getStatisticByPerTour(
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

    @GetMapping("/guide")
    public ResponseEntity<Result> getStatisticByPerGuide(Authentication authentication) {
        return AuthUtils.checkAuthentication(
                authentication,
                () -> {
                    try {
                        return new ResponseEntity<>(
                                new Result(
                                        false,
                                        HttpStatus.OK.value(),
                                        "get statistic of per guide successfully",
                                        statisticService.getStatisticByPerGuide(
                                                ((CustomUserDetails) authentication.getPrincipal()).getId())),
                                HttpStatus.OK);
                    } catch (Exception e) {
                        return new ResponseEntity<>(
                                new Result(
                                        false, HttpStatus.CONFLICT.value(), "Get statistics of failed per guide", null),
                                HttpStatus.CONFLICT);
                    }
                });
    }

    @GetMapping("/guide/tours")
    public ResponseEntity<Result> getStatisticOfToursByGuide(
            Authentication authentication) {
        return AuthUtils.checkAuthentication(
                authentication,
                () -> {
                    try {
                        return new ResponseEntity<>(
                                new Result(
                                        false,
                                        HttpStatus.OK.value(),
                                        "get statistic of tours by guide successfully",
                                        statisticService.getStatisticOfToursByGuide(
                                                ((CustomUserDetails) authentication.getPrincipal()).getId())),
                                HttpStatus.OK);
                    } catch (Exception e) {
                        return new ResponseEntity<>(
                                new Result(
                                        false, HttpStatus.CONFLICT.value(), "Get statistics of tours by guide failure", null),
                                HttpStatus.CONFLICT);
                    }
                });
    }
}
