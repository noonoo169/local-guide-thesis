package com.example.localguidebe.service;

import com.example.localguidebe.dto.responsedto.StatisticalGuideDTO;
import com.example.localguidebe.dto.responsedto.StatisticalTourDTO;

import java.util.List;

public interface StatisticService {
    List<StatisticalGuideDTO> getStatisticalByGuide();

    Double getRevenueByGuide(Long guideId);

    Double getRevenueByTour(Long tourId);

    Long getTotalTravelerNumberByTour(Long tourId);

    Long getTotalTravelerNumberByGuide(Long guideId);

    List<StatisticalTourDTO> getStatisticalByTour();

    Long getTotalBookingByTour(Long tourId);

    Long getTotalBookingByGuide(Long guideId);

    StatisticalTourDTO getStatisticByPerTour(Long tourId);

    StatisticalGuideDTO getStatisticByPerGuide(Long guideId);

    List<StatisticalTourDTO> getStatisticOfToursByGuide(
            Long guideId);
}
