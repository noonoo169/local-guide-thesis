package com.example.localguidebe.dto;

public record TravelerRequestDTO(
    Long id,
    String transportation,
    Integer duration,
    String unit,
    Double maxPrice,
    String destination,
    String message,
    GuideDTO guide,
    UserDTO traveler,
    Long tourId) {}
