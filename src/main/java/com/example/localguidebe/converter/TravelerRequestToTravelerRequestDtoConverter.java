package com.example.localguidebe.converter;

import com.example.localguidebe.dto.TravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;
import org.springframework.stereotype.Component;

@Component
public class TravelerRequestToTravelerRequestDtoConverter {
  private final UserToUserDtoConverter userToUserDtoConverter;
  private final UserToGuideDtoConverter userToGuideDtoConverter;

  public TravelerRequestToTravelerRequestDtoConverter(
      UserToUserDtoConverter userToUserDtoConverter,
      UserToGuideDtoConverter userToGuideDtoConverter) {
    this.userToUserDtoConverter = userToUserDtoConverter;
    this.userToGuideDtoConverter = userToGuideDtoConverter;
  }

  public TravelerRequestDTO convert(TravelerRequest source) {
    return new TravelerRequestDTO(
        source.getId(),
        source.getTransportation(),
        source.getDuration(),
        source.getUnit(),
        source.getMaxPrice(),
        source.getDestination(),
        source.getMessage(),
        userToGuideDtoConverter.convert(source.getGuide()),
        userToUserDtoConverter.convert(source.getTraveler()),
        source.getTour() == null ? null : source.getTour().getId());
  }
}
