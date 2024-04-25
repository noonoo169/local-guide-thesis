package com.example.localguidebe.dto;

import com.example.localguidebe.enums.RolesEnum;

import java.sql.Timestamp;
import java.util.Set;

public record UserDTO(
        Long id,
    String email,
    String username,
    Timestamp dateOfBirth,
    String phone,
    String address,
    Set<RolesEnum> roles
){}
