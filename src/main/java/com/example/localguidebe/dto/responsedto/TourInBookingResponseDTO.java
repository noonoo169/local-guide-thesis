package com.example.localguidebe.dto.responsedto;

import com.example.localguidebe.dto.ImageDTO;
import com.example.localguidebe.entity.Image;

import java.util.List;

public record TourInBookingResponseDTO(
    Long id, String name, Double overallRating,
    List<Image> images
) {}
