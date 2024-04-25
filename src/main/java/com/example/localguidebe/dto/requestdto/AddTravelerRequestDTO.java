package com.example.localguidebe.dto.requestdto;

public record AddTravelerRequestDTO(
    Long guideId,
    String transportation,
    Integer duration,
    String unit,
    Double maxPrice,
    String destination,
    String message) {}
