package com.example.localguidebe.service;

import com.example.localguidebe.dto.responsedto.StatisticalGuidePaginationDTO;
import com.example.localguidebe.dto.responsedto.StatisticalTourDTO;
import com.example.localguidebe.dto.responsedto.StatisticalTourPaginationDTO;

public interface StatisticService {
  StatisticalGuidePaginationDTO getStatisticalByGuide(Integer page, Integer limit, String order);

  Double getRevenueByGuide(Long guideId);

  Double getRevenueByTour(Long tourId);

  Long getTotalTravelerNumberByTour(Long tourId);

  Long getTotalTravelerNumberByGuide(Long guideId);

  StatisticalTourPaginationDTO getStatisticalByTour(Integer page, Integer limit, String order);

  Long getTotalBookingByTour(Long tourId);

  Long getTotalBookingByGuide(Long guideId);

  StatisticalTourDTO getStatisticByPerTour(Long tourId);
}
