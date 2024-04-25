package com.example.localguidebe.converter;

import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.entity.Tour;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class TourToTourDtoConverter {
    private final ImageToImageDtoConverter imageToImageDtoConverter;
    private final UserToUserDtoConverter userToUserDtoConverter;

    private final CategoryToCategoryDtoConverter categoryToCategoryDtoConverter;


    public TourToTourDtoConverter(ImageToImageDtoConverter imageToImageDtoConverter,
                                  UserToUserDtoConverter userToUserDtoConverter,
                                  CategoryToCategoryDtoConverter categoryToCategoryDtoConverter) {
        this.imageToImageDtoConverter = imageToImageDtoConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.categoryToCategoryDtoConverter = categoryToCategoryDtoConverter;
    }




    public TourDTO convert(Tour tour){
        return new TourDTO(tour.getId(),
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
                tour.getOverallRating(),
                tour.getItinerary(),
                userToUserDtoConverter.convert(tour.getGuide()),


                tour.getCategories() != null ? tour.getCategories().stream().map(categoryToCategoryDtoConverter::convertCategory).collect(Collectors.toSet()):
                        null,
                tour.getImages() != null ? tour.getImages().stream().map(imageToImageDtoConverter::convertImageDTO).collect(Collectors.toList()):null);

    }

}
