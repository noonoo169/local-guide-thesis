package com.example.localguidebe.service;



import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;

import com.example.localguidebe.dto.TourDTO;


import com.example.localguidebe.dto.TourDTO;

import com.example.localguidebe.dto.responsedto.SearchTourDTO;
import com.example.localguidebe.entity.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TourService {
    List<TourDTO> getListTour();
    Tour saveTour(TourRequestDTO tourRequestDTO);

    Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO);

    TourDTO getTourById(Long id);
    SearchTourDTO getTours(Integer page,
                           Integer limit,
                           String sortBy,
                           String order,
                           String searchName,
                           Double minPrice,
                           Double maxPrice,
                           Long categoryId);

}
