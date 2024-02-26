package com.example.localguidebe.converter;

import com.example.localguidebe.dto.TourDTO;
import com.example.localguidebe.entity.Tour;
import java.util.stream.Collectors;

import com.example.localguidebe.enums.AssociateName;
import com.example.localguidebe.repository.ImageRepository;
import com.example.localguidebe.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourToTourDtoConverter {
  private final ImageToImageDtoConverter imageToImageDtoConverter;
  private final UserToUserDtoConverter userToUserDtoConverter;

  private final CategoryToCategoryDtoConverter categoryToCategoryDtoConverter;
  private final ImageService imageService;
  @Autowired
  public TourToTourDtoConverter(
      ImageToImageDtoConverter imageToImageDtoConverter,
      UserToUserDtoConverter userToUserDtoConverter,
      CategoryToCategoryDtoConverter categoryToCategoryDtoConverter,
      ImageService imageService) {
    this.imageToImageDtoConverter = imageToImageDtoConverter;
    this.userToUserDtoConverter = userToUserDtoConverter;
    this.categoryToCategoryDtoConverter = categoryToCategoryDtoConverter;
    this.imageService = imageService;
  }

  public TourDTO convert(Tour tour) {
    return new TourDTO(
        tour.getId(),
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
        tour.getOverallRating()!= null?tour.getOverallRating():0.0,
        tour.getItinerary(),
        tour.getIsDeleted(),
        tour.getAddress(),
        tour.getGuide() != null ? userToUserDtoConverter.convert(tour.getGuide()) : null,
        tour.getCategories() != null
            ? tour.getCategories().stream()
                .map(categoryToCategoryDtoConverter::convertCategory)
                .collect(Collectors.toSet())
            : null,

        imageService.getImageByAssociateIddAndAssociateName(tour.getId(), AssociateName.TOUR));

  }
}
