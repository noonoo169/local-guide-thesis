package com.example.localguidebe.service;

import com.example.localguidebe.entity.Tour;

import java.util.List;

public interface TourService {
    List<Tour> getListTour();
    Tour saveTour(Tour tour);

    Tour getTourById(Long id);
}
