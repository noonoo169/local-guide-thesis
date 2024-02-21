package com.example.localguidebe.dto;

import com.example.localguidebe.enums.RolesEnum;
import java.time.LocalDateTime;
import java.util.Set;

public record UserDTO(
    Long id,
    String email,
    String username,
    LocalDateTime dateOfBirth,
    String phone,
    String address,
    Set<RolesEnum> roles) {}
