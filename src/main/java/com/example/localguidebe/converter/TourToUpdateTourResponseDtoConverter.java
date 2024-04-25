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

    public TourToUpdateTourResponseDtoConverter(LocationToLocationDto locationToLocationDto,
                                                TourStartTimeToTourStartTimeDtoConverter tourStartTimeToTourStartTimeDtoConverter,
                                                ImageToImageDtoConverter imageToImageDtoConverter) {
        this.locationToLocationDto = locationToLocationDto;
        this.tourStartTimeToTourStartTimeDtoConverter = tourStartTimeToTourStartTimeDtoConverter;
        this.imageToImageDtoConverter = imageToImageDtoConverter;
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
                locationToLocationDto.convert(source.getProvince()),
                source.getTourStartTimes().stream().map(tourStartTimeToTourStartTimeDtoConverter::convert).toList(),
                CategoryToCategoryDtoConverter.convertSetCategory(source.getCategories()),
                source.getImages().stream().map(imageToImageDtoConverter::convert).toList(),
                source.getLocations().stream().map(locationToLocationDto::convert).collect(Collectors.toSet()),
                locationToLocationDto.convert(source.getMeetingPoint()));
    }
}
