package com.example.localguidebe.dto;

import com.example.localguidebe.entity.GuideDetail;
import jakarta.persistence.Column;

import java.sql.Timestamp;
import java.util.List;

public record GuideDTO (
        Long id,
        String email,
        String username,
        Timestamp dateOfBirth,
        String phone,
        String address,
        String biography,
        String credential,
        Double overallRating,
        List<LanguageSkillDTO> languageSkill
) {
}
