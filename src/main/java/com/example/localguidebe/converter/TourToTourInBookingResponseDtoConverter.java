package com.example.localguidebe.converter;

import com.example.localguidebe.dto.responsedto.TourInBookingResponseDTO;
import com.example.localguidebe.entity.Tour;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class TourToTourInBookingResponseDtoConverter {
  private final ImageToImageDtoConverter imageToImageDtoConverter;

  public TourToTourInBookingResponseDtoConverter(
      ImageToImageDtoConverter imageToImageDtoConverter) {
    this.imageToImageDtoConverter = imageToImageDtoConverter;
  }

  public TourInBookingResponseDTO convert(Tour source) {
    return new TourInBookingResponseDTO(
        source.getId(),
        source.getName(),
        source.getOverallRating(),
        source.getImages() != null
            ? source.getImages().stream()
                .map(imageToImageDtoConverter::convertImageDTO)
                .collect(Collectors.toList())
            : null);
  }
}
