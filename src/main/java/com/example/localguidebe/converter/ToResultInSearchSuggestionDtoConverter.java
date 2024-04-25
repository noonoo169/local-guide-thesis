package com.example.localguidebe.converter;

import com.example.localguidebe.dto.responsedto.ResultInSearchSuggestionDTO;
import com.example.localguidebe.entity.Tour;
import com.example.localguidebe.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ToResultInSearchSuggestionDtoConverter {
  public ResultInSearchSuggestionDTO convert(Object object) {
    if (object instanceof Tour tour) {
      return new ResultInSearchSuggestionDTO(
          tour.getId(), tour.getName(), tour.getImagesOfObject().isEmpty() ? null: tour.getImagesOfObject().get(0).getImageLink());
    } else if (object instanceof User user) {
      return new ResultInSearchSuggestionDTO(
              user.getId(), user.getUsername(), user.getImagesOfObject().isEmpty() ? null: user.getImagesOfObject().get(0).getImageLink());
    }
    return new ResultInSearchSuggestionDTO(null,null, null);
  }
}
