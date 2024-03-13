package com.example.localguidebe.service.impl;

import com.example.localguidebe.dto.requestdto.AddTravelerRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.enums.TravelerRequestStatus;
import com.example.localguidebe.repository.TravelerRequestRepository;
import com.example.localguidebe.service.TravelerRequestService;
import com.example.localguidebe.service.UserService;
import java.util.List;
import org.springframework.beans.BeanUtils;
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
  public TravelerRequest findTravelerRequestById(Long id) {
    return travelerRequestRepository.findById(id).orElse(null);
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
            .transportation(String.join(", ", addTravelerRequestDTO.transportation()))
            .unit(addTravelerRequestDTO.unit())
            .duration(addTravelerRequestDTO.duration())
            .status(TravelerRequestStatus.PENDING)
            .traveler(traveler)
            .guide(guide)
            .build();

    return travelerRequestRepository.save(travelerRequest);
  }

  @Override
  public List<TravelerRequest> getTravelerRequests(String email) {
    return travelerRequestRepository.getTravelerRequests(email);
  }

  @Override
  public TravelerRequest updateTravelerRequests(
      String email, UpdateTravelerRequestDTO updateTravelerRequestDTO, Long travelerRequestId) {
    TravelerRequest travelerRequest = findTravelerRequestById(travelerRequestId);
    if (travelerRequest == null) return null;
    if (!travelerRequest.getTraveler().getEmail().equals(email)
        && !travelerRequest.getGuide().getEmail().equals(email)) return null;
    BeanUtils.copyProperties(updateTravelerRequestDTO, travelerRequest, "transportation", "status");
    travelerRequest.setTransportation(String.join(", ", updateTravelerRequestDTO.transportation()));
    return travelerRequestRepository.save(travelerRequest);
  }

  @Override
  public TravelerRequest updateTravelRequestStatus(
      String email, UpdateTravelerRequestDTO updateTravelerRequestDTO, Long travelerRequestId) {
    TravelerRequest travelerRequest = findTravelerRequestById(travelerRequestId);
    if (travelerRequest == null) return null;

    // If user is traveler, they only delete there request
    User user = userService.findUserByEmail(email);
    if (user.getRoles().stream().noneMatch(role -> role.getName().equals(RolesEnum.GUIDER))
        && !updateTravelerRequestDTO.status().equals(TravelerRequestStatus.DELETED)) return null;

    if (!travelerRequest.getTraveler().getEmail().equals(email)
        && !travelerRequest.getGuide().getEmail().equals(email)) return null;

    travelerRequest.setStatus(updateTravelerRequestDTO.status());
    return travelerRequestRepository.save(travelerRequest);
  }
}
