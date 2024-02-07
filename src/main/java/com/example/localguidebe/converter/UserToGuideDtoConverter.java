package com.example.localguidebe.converter;

import com.example.localguidebe.dto.GuideDTO;
import com.example.localguidebe.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserToGuideDtoConverter {
    private final LanguageSkillToLanguageSkillDtoConverter languageSkillToLanguageSkillDtoConverter;

    public UserToGuideDtoConverter(LanguageSkillToLanguageSkillDtoConverter languageSkillToLanguageSkillDtoConverter) {
       this.languageSkillToLanguageSkillDtoConverter = languageSkillToLanguageSkillDtoConverter;
    }

    public GuideDTO convert(User source) {
        return new GuideDTO(source.getId(),
                source.getEmail(),
                source.getUsername(),
                source.getDateOfBirth(),
                source.getPhone(),
                source.getAddress(),
                source.getBiography(),
                source.getCredential(),
                source.getOverallRating(),
                source.getLanguageSkills().stream().map(languageSkillToLanguageSkillDtoConverter::convert).toList()
                );
    }
}
