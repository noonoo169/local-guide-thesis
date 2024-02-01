package com.example.localguidebe.converter;

import com.example.localguidebe.dto.UserDTO;
import com.example.localguidebe.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserToUserDtoConverter {
    private final RoleToRoleDtoConverter roleToRoleDtoConverter;

    public UserToUserDtoConverter(RoleToRoleDtoConverter roleToRoleDtoConverter) {
        this.roleToRoleDtoConverter = roleToRoleDtoConverter;
    }

    public UserDTO convert(User source) {
        return new UserDTO(
                source.getId(),
                source.getEmail(),
                source.getUsername(),
                source.getDateOfBirth(),
                source.getPhone(),
                source.getAddress(),
                source.getRoles() != null ? source.getRoles().stream().map(roleToRoleDtoConverter::convert).collect(Collectors.toSet()) : null);
    }
}
