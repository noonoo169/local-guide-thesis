package com.example.localguidebe.controller;

import com.example.localguidebe.converter.UserToUserDtoConverter;
import com.example.localguidebe.dto.requestdto.UpdatePersonalInformationDTO;
import com.example.localguidebe.dto.responsedto.IsCanReviewResponseDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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

  @GetMapping("/{guideId}")
  public ResponseEntity<Result> isCanCommentOnGuide(
      Authentication authentication, @PathVariable("guideId") Long guideId) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          String travelerEmail = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
          User traveler = userService.findUserByEmail(travelerEmail);
          if (!userService.isTravelerCanAddReviewForGuide(traveler, guideId)) {
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        false,
                        HttpStatus.OK.value(),
                        "You can't add review for this guide",
                        new IsCanReviewResponseDTO(false)));
          } else
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        true,
                        HttpStatus.OK.value(),
                        "You can add review for this guide",
                        new IsCanReviewResponseDTO(true)));
        });
  }

  @GetMapping("/me")
  public ResponseEntity<Result> getSelfInformation(Authentication authentication) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            String email = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
            User user = userService.findUserByEmail(email);
            if (user == null) {
              return ResponseEntity.status(HttpStatus.OK)
                  .body(new Result(false, HttpStatus.OK.value(), "The user not exist"));
            }
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        false,
                        HttpStatus.OK.value(),
                        "Get user information successfully",
                        userToUserDtoConverter.convert(user)));

          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    new Result(
                        true,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Can not get personal information"));
          }
        });
  }

  @DeleteMapping("/{userId}")
  public ResponseEntity<Result> deleteUser(
      Authentication authentication, @PathVariable("userId") Long userId) {
    return AuthUtils.checkAuthentication(
        authentication,
        () -> {
          try {
            String email = ((CustomUserDetails) authentication.getPrincipal()).getEmail();
            User userInSession = userService.findUserByEmail(email);
            User userBeDeleted = userService.findById(userId).orElse(null);

            if (userBeDeleted == null) {
              return ResponseEntity.status(HttpStatus.OK)
                  .body(new Result(false, HttpStatus.OK.value(), "The user not exist"));
            }
            if (!Objects.equals(userInSession.getId(), userBeDeleted.getId())) {
              if (userToUserDtoConverter.convert(userInSession).role() != RolesEnum.ADMIN) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(
                        new Result(
                            false, HttpStatus.CONFLICT.value(), "You can not delete this user"));
              }
            }
            return ResponseEntity.status(HttpStatus.OK)
                .body(
                    new Result(
                        true,
                        HttpStatus.OK.value(),
                        "Delete user successfully",
                        userToUserDtoConverter.convert(userService.deleteUser(userBeDeleted))));

          } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                    new Result(
                        false,
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "You can not delete this user"));
          }
        });
  }
}
