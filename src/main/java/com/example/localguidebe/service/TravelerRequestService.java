package com.example.localguidebe.service;

import com.example.localguidebe.dto.requestdto.AddTravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;
import java.util.List;

public interface TravelerRequestService {
  TravelerRequest addTravelerRequest(String email, AddTravelerRequestDTO addTravelerRequestDTO);

  List<TravelerRequest> getTravelerRequests(String email);
}
