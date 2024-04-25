package com.example.localguidebe.controller;

import com.example.localguidebe.converter.UserToUserDtoConverter;
import com.example.localguidebe.dto.requestdto.UpdatePersonalInformationDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;
  private final UserToUserDtoConverter userToUserDtoConverter;

  public UserController(UserService userService, UserToUserDtoConverter userToUserDtoConverter) {
    this.userService = userService;
    this.userToUserDtoConverter = userToUserDtoConverter;
  }

  @PutMapping("")
  public ResponseEntity<Result> updatePersonalInformation(
      Authentication authentication,
      @RequestBody UpdatePersonalInformationDTO updatePersonalInformationDTO) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            String email = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
            User updatedUser =
                userService.updatePersonalInformation(email, updatePersonalInformationDTO);
            if (updatedUser == null) {
              return ResponseEntity.status(HttpStatus.NOT_FOUND)
                  .body(new Result(true, HttpStatus.NOT_FOUND.value(), "This user is not exist"));
            }
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        true,
                        HttpStatus.OK.value(),
                        "Update personal information successfully",
                        userToUserDtoConverter.convert(updatedUser)));
          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    new Result(
                        true,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Can not update personal information"));
          }
        });
  }
}
