package com.example.localguidebe.dto;

import com.example.localguidebe.enums.GuideApplicationStatus;

public record GuideApplicationDTO(
    Long id,
    Boolean isLicensedGuide,
    String transportation,
    Integer yearsOfExperience,
    String howGuideHearAboutUs,
    GuideApplicationStatus status,
    String reasonDeny,
    UserDTO user) {}
