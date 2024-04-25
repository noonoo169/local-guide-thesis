package com.example.localguidebe.controller;

import com.example.localguidebe.converter.TravelerRequestToTravelerRequestDtoConverter;
import com.example.localguidebe.dto.requestdto.AddTravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.TravelerRequestService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traveler-requests")
public class TravelerRequestController {
  private final TravelerRequestService travelerRequestService;
  private final TravelerRequestToTravelerRequestDtoConverter
      travelerRequestToTravelerRequestDtoConverter;

  public TravelerRequestController(
      TravelerRequestService travelerRequestService,
      TravelerRequestToTravelerRequestDtoConverter travelerRequestToTravelerRequestDtoConverter) {
    this.travelerRequestService = travelerRequestService;
    this.travelerRequestToTravelerRequestDtoConverter =
        travelerRequestToTravelerRequestDtoConverter;
  }

  @PostMapping("")
  public ResponseEntity<Result> addTravelerRequest(
      Authentication authentication, @RequestBody AddTravelerRequestDTO addTravelerRequestDTO) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          String email = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
          TravelerRequest travelerRequest =
              travelerRequestService.addTravelerRequest(email, addTravelerRequestDTO);
          if (travelerRequest == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                    new Result(
                        false,
                        HttpStatus.CONFLICT.value(),
                        "You can't not add request for this guide"));
          }
          return ResponseEntity.status(HttpStatus.OK)
              .body(
                  new Result(
                      true,
                      HttpStatus.OK.value(),
                      "Your request will be processed soon.",
                      travelerRequestToTravelerRequestDtoConverter.convert(travelerRequest)));
        });
  }
}
