package com.example.localguidebe.dto;

import jakarta.persistence.Column;

public record LocationDTO (
        Long id,
        String name,
        String latitude,
        String longitude
){}
