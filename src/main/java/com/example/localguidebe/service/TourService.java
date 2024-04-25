package com.example.localguidebe.service;

import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.entity.Tour;

import java.util.List;

public interface TourService {
    List<Tour> getListTour();
    Tour saveTour(Tour tour);
    Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO);
    Tour getTourById(Long id);
}
