package com.example.localguidebe.dto.requestdto;

import java.util.List;

public record AddTravelerRequestDTO(
    Long guideId,
    List<String> transportation,
    Integer duration,
    String unit,
    Double maxPrice,
    String destination,
    String message) {}
