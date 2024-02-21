package com.example.localguidebe.converter;

import com.example.localguidebe.dto.responsedto.UpdateTourResponseDTO;
import com.example.localguidebe.entity.Tour;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TourToUpdateTourResponseDtoConverter {
    private final LocationToLocationDto locationToLocationDto;
    private final TourStartTimeToTourStartTimeDtoConverter tourStartTimeToTourStartTimeDtoConverter;
    private final ImageToImageDtoConverter imageToImageDtoConverter;

    private final CategoryToCategoryDtoConverter categoryToCategoryDtoConverter;

    public TourToUpdateTourResponseDtoConverter(LocationToLocationDto locationToLocationDto,
                                                TourStartTimeToTourStartTimeDtoConverter tourStartTimeToTourStartTimeDtoConverter,
                                                ImageToImageDtoConverter imageToImageDtoConverter,
                                                CategoryToCategoryDtoConverter categoryToCategoryDtoConverter) {
        this.locationToLocationDto = locationToLocationDto;
        this.tourStartTimeToTourStartTimeDtoConverter = tourStartTimeToTourStartTimeDtoConverter;
        this.imageToImageDtoConverter = imageToImageDtoConverter;
        this.categoryToCategoryDtoConverter = categoryToCategoryDtoConverter;
    }

    public UpdateTourResponseDTO convert(Tour source) {
        return new UpdateTourResponseDTO(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getTransportation(),
                source.getIncludeService(),
                source.getDuration(),
                source.getUnit(),
                source.getEstimatedLocalCashNeeded(),
                source.getPricePerTraveler(),
                source.getLimitTraveler(),
                source.getExtraPrice(),
                source.getItinerary(),
                // TODO : fix province to String here
                source.getProvince(),
                source.getTourStartTimes().stream().map(tourStartTimeToTourStartTimeDtoConverter::convert).toList(),
                source.getCategories() != null ?source.getCategories().stream().map(categoryToCategoryDtoConverter::convertCategory).collect(Collectors.toSet()) : null,
                source.getImages().stream().map(imageToImageDtoConverter::convert).toList(),
                source.getLocations().stream().map(locationToLocationDto::convert).collect(Collectors.toSet()),
                locationToLocationDto.convert(source.getMeetingPoint()));
    }
}
