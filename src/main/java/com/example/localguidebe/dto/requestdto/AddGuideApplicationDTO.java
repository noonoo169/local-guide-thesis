package com.example.localguidebe.dto.requestdto;

import java.time.LocalDateTime;

public record AddGuideApplicationDTO(
    Long userId,
    String email,
    String password,
    String fullName,
    LocalDateTime dateOfBirth,
    String phone,
    String address,
    Boolean isLicensedGuide,
    String transportation,
    Integer yearsOfExperience,
    String howGuideHearAboutUs) {}
