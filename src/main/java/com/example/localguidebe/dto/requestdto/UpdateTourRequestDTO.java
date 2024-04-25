package com.example.localguidebe.dto.requestdto;

import com.example.localguidebe.entity.Location;
import com.example.localguidebe.entity.TourStartTime;
import java.util.List;
import java.util.Set;

public record UpdateTourRequestDTO(
    Long id,
    String name,
    String description,
    String transportation,
    String includeService,
    Integer duration,
    String unit,
    String estimatedLocalCashNeeded,
    Double pricePerTraveler,
    Integer limitTraveler,
    Double extraPrice,
    String address,
    String itinerary,
    Integer guide_id,
    List<TourStartTime> tourStartTimes,
    List<Long> category_ids,
    List<String> image_ids,
    Set<Location> locations,
    Location meetingPoint) {}
