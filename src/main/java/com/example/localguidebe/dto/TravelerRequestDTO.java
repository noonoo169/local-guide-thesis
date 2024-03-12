package com.example.localguidebe.dto;

import java.util.List;

public record TravelerRequestDTO(
    Long id,
    List<String> transportation,
    Integer duration,
    String unit,
    Double maxPrice,
    String destination,
    String message,
    GuideDTO guide,
    UserDTO traveler,
    Long tourId) {}
