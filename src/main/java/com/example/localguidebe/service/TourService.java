package com.example.localguidebe.service;



import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;

import com.example.localguidebe.dto.TourDTO;


import com.example.localguidebe.dto.TourDTO;

import com.example.localguidebe.entity.Tour;

import java.util.List;

public interface TourService {
    List<TourDTO> getListTour();
    Tour saveTour(TourRequestDTO tourRequestDTO);

    Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO);

    List<TourDTO> searchTour(String nameTour);
    TourDTO getTourById(Long id);





}
