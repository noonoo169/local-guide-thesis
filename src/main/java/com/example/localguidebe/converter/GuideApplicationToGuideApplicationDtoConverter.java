package com.example.localguidebe.converter;

import com.example.localguidebe.dto.GuideApplicationDTO;
import com.example.localguidebe.entity.GuideApplication;
import org.springframework.stereotype.Component;

@Component
public class GuideApplicationToGuideApplicationDtoConverter {
  private final UserToUserDtoConverter userToUserDtoConverter;

  public GuideApplicationToGuideApplicationDtoConverter(
      UserToUserDtoConverter userToUserDtoConverter) {
    this.userToUserDtoConverter = userToUserDtoConverter;
  }

  public GuideApplicationDTO convert(GuideApplication source) {
    return new GuideApplicationDTO(
        source.getId(),
        source.getIsLicensedGuide(),
        source.getTransportation(),
        source.getYearsOfExperience(),
        source.getHowGuideHearAboutUs(),
        source.getStatus(),
        source.getReasonDeny(),
        userToUserDtoConverter.convert(source.getUser()));
  }
}
