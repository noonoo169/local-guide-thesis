package com.example.localguidebe.service;

import com.example.localguidebe.dto.requestdto.AddTravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;

public interface TravelerRequestService {
  TravelerRequest addTravelerRequest(String email, AddTravelerRequestDTO addTravelerRequestDTO);
}
