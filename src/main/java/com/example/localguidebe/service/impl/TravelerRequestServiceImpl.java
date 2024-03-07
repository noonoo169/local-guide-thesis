package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.requestdto.AddTravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.TravelerRequestStatus;
import com.example.localguidebe.repository.TravelerRequestRepository;
import com.example.localguidebe.service.TravelerRequestService;
import com.example.localguidebe.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class TravelerRequestServiceImpl implements TravelerRequestService {
  private final TravelerRequestRepository travelerRequestRepository;
  private final UserService userService;

  public TravelerRequestServiceImpl(
      TravelerRequestRepository travelerRequestRepository, UserService userService) {
    this.travelerRequestRepository = travelerRequestRepository;
    this.userService = userService;
  }

  @Override
  public TravelerRequest addTravelerRequest(
      String email, AddTravelerRequestDTO addTravelerRequestDTO) {
    User traveler = userService.findUserByEmail(email);
    User guide = userService.findById(addTravelerRequestDTO.guideId()).orElse(null);
    if (guide == null) return null;

    TravelerRequest travelerRequest =
        TravelerRequest.builder()
            .destination(addTravelerRequestDTO.destination())
            .maxPrice(addTravelerRequestDTO.maxPrice())
            .message(addTravelerRequestDTO.message())
            .transportation(addTravelerRequestDTO.transportation())
            .unit(addTravelerRequestDTO.unit())
            .duration(addTravelerRequestDTO.duration())
            .status(TravelerRequestStatus.PENDING)
            .traveler(traveler)
            .guide(guide)
            .build();

    return travelerRequestRepository.save(travelerRequest);
  }
}
