package com.example.localguidebe.converter;

import com.example.localguidebe.dto.CategoryDTO;
import com.example.localguidebe.dto.responsedto.TourResponseDTO;
import com.example.localguidebe.entity.Category;
import com.example.localguidebe.entity.Tour;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TourToTourDtoConverter {
    public TourResponseDTO convert(Tour tour){
        return new TourResponseDTO(tour.getId(),
                                    tour.getName(),
                                    tour.getDescription(),
                                    tour.getTransportation(),
                                    tour.getIncludeService(),
                                    tour.getDuration(),
                                    tour.getUnit(),
                                    tour.getEstimatedLocalCashNeeded(),
                                    tour.getPricePerTraveler(),
                                    tour.getLimitTraveler(),
                                    tour.getExtraPrice(),
                                    tour.getItinerary(),
                                    tour.getCategories() != null ? CategoryToCategoryDtoConverter.convertSetCategory(tour.getCategories()) :
                        null);
    }

}
