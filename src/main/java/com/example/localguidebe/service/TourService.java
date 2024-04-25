package com.example.localguidebe.service;

import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.dto.requestdto.TourRequestDTO;
import com.example.localguidebe.dto.requestdto.UpdateTourRequestDTO;
import com.example.localguidebe.dto.responsedto.SearchSuggestionResponseDTO;
import com.example.localguidebe.dto.responsedto.SearchTourDTO;
import com.example.localguidebe.entity.Tour;
import java.time.LocalDate;
import java.util.List;

public interface TourService {
  List<TourDTO> getListTour();

  Tour saveTour(TourRequestDTO tourRequestDTO, String email);

  Tour updateTour(UpdateTourRequestDTO updateTourRequestDTO);

  TourDTO getTourById(Long id);

  SearchTourDTO getTours(
      Integer page,
      Integer limit,
      String sortBy,
      String order,
      String searchKey,
      Double minPrice,
      Double maxPrice,
      List<Long> categoryId);

  List<TourDTO> deleteTour(Long id);

  List<Tour> getToursOfGuide(Long guideId);

  SearchSuggestionResponseDTO getTourAndTourLocations(String searchValue);

  List<String> getTourStartTimeAvailable(Long tourId, LocalDate localDate);

  boolean checkBookingByTraveler(Long tourId, String email);

  void updateRatingForTour(Tour tour);
}
