package com.example.localguidebe.converter;

import com.example.localguidebe.dto.TravelerRequestDTO;
import com.example.localguidebe.entity.TravelerRequest;
import java.util.List;
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
        List.of(source.getTransportation().split(", ")),
        source.getDuration(),
        source.getUnit(),
        source.getMaxPricePerPerson(),
        source.getNumberOfTravelers(),
        source.getDestination(),
        source.getMessage(),
        source.getStatus(),
        userToGuideDtoConverter.convert(source.getGuide()),
        userToUserDtoConverter.convert(source.getTraveler()),
        source.getTour() == null ? null : source.getTour().getId());
  }
}
