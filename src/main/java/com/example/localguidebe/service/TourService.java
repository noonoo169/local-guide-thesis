package com.example.localguidebe.service;

import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.dto.responsedto.SearchSuggestionResponseDTO;
import com.example.localguidebe.dto.responsedto.SearchTourDTO;
import com.example.localguidebe.entity.Tour;
import java.util.List;

public interface TourService {
  List<TourDTO> getListTour();

  Tour saveTour(TourRequestDTO tourRequestDTO,String email);

  Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO);

  TourDTO getTourById(Long id);

  SearchTourDTO getTours(
      Integer page,
      Integer limit,
      String sortBy,
      String order,
      String searchName,
      Double minPrice,
      Double maxPrice,
      List<Long> categoryId);
  SearchTourDTO getToursByNameAndAddress(
          Integer page,
          Integer limit,
          String sortBy,
          String order,
          List<String> searchNames,
          List<String> addresses,
          Double minPrice,
          Double maxPrice,
          List<Long> categoryId);

  List<TourDTO> deleteTour(Long id);

  List<Tour> getToursOfGuide(String email);

  SearchSuggestionResponseDTO getTourAndTourAddresses(String searchValue);
}
