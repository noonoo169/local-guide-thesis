package com.example.localguidebe.controller;

import com.example.localguidebe.converter.UserToLoginResponseDtoConverter;
import com.example.localguidebe.dto.fcmdto.DeviceNotificationRequest;
import com.example.localguidebe.dto.fcmdto.NotificationSubscriptionRequest;
import com.example.localguidebe.dto.fcmdto.TopicNotificationRequest;
import com.example.localguidebe.dto.requestdto.LoginRequestDTO;
import com.example.localguidebe.dto.requestdto.ResetPasswordRequestDTO;
import com.example.localguidebe.dto.requestdto.UserAuthDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.exception.ExceedLimitTravelerOfTourException;
import com.example.localguidebe.security.jwt.JwtTokenProvider;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.EmailService;
import com.example.localguidebe.service.FcmService;
import com.example.localguidebe.service.RoleService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.JsonUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;
  private final RoleService roleService;
  private final EmailService emailService;
  private final UserToLoginResponseDtoConverter userToLoginResponseDtoConverter;
  private final FcmService fcmService;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public AuthController(
      UserService userService,
      RoleService roleService,
      EmailService emailService,
      UserToLoginResponseDtoConverter userToLoginResponseDtoConverter,
      FcmService fcmService,
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.roleService = roleService;
    this.emailService = emailService;
    this.userToLoginResponseDtoConverter = userToLoginResponseDtoConverter;
    this.fcmService = fcmService;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/register")
  public ResponseEntity<Result> registerUser(@RequestBody UserAuthDTO userAuthDTO) {
    if (userService.getAllUser().stream()
        .anyMatch(user -> user.getEmail().equals(userAuthDTO.getEmail()))) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Result(true, HttpStatus.CONFLICT.value(), "Duplicate account"));
    }
    User user = new User();
    user.setEmail(userAuthDTO.getEmail());
    user.setPassword(passwordEncoder.encode(userAuthDTO.getPassword()));
    user.getRoles()
        .add(
            roleService.getRoleList().stream()
                .filter(role -> role.getName().equals(RolesEnum.TRAVELER))
                .findFirst()
                .orElseThrow());
    userService.saveUser(user);
    String accessToken = jwtTokenProvider.generateToken(userAuthDTO.getEmail());

    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Result(
                true,
                HttpStatus.OK.value(),
                "User login successfully",
                userToLoginResponseDtoConverter.convert(user, accessToken)));
  }

  @PostMapping("/login")
  public ResponseEntity<Result> login(@RequestBody LoginRequestDTO userLoginDTO) {
    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  userLoginDTO.getEmail(), userLoginDTO.getPassword()));
      CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

      User user = userService.findUserByEmail(userLoginDTO.getEmail());
      String accessToken = jwtTokenProvider.generateToken(customUserDetails.getEmail());
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              new Result(
                  true,
                  HttpStatus.OK.value(),
                  "User login successfully",
                  userToLoginResponseDtoConverter.convert(user, accessToken)));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new Result(false, HttpStatus.UNAUTHORIZED.value(), "Email or password not match"));
    }
  }

  @PostMapping("/send-email-reset-password")
  public ResponseEntity<Result> sendEmailResetPassword(
      @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
    try {
      String email = resetPasswordRequestDTO.email();
      User user = userService.findUserByEmail(email);
      if (user == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new Result(false, HttpStatus.NOT_FOUND.value(), "Email has not been sign up"));
      }
      String token = jwtTokenProvider.generateTokenResetPassword(email);
      user.setRpToken(token);
      userService.saveUser(user);
      if (!emailService.sendEmailForResetPassword(email, token)) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new Result(
                    false,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Can't send email for this email"));
      }
      return ResponseEntity.status(HttpStatus.OK)
          .body(new Result(true, HttpStatus.OK.value(), "Please check your email"));

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new Result(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error"));
    }
  }

  @PostMapping("/reset-password")
  public ResponseEntity<Result> resetPassword(
      @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
    String token = resetPasswordRequestDTO.token();
    if (!jwtTokenProvider.validateToken(token)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Result(false, HttpStatus.CONFLICT.value(), "This link has expired"));
    }
    String email = jwtTokenProvider.getEmailFromToken(token);
    User user = userService.findUserByEmail(email);
    if (user.getRpToken() == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Result(false, HttpStatus.CONFLICT.value(), "You has reset password"));
    }
    if (!user.getRpToken().equals(token)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Result(false, HttpStatus.CONFLICT.value(), "Token is not your own"));
    }
    user.setRpToken(null);
    user.setPassword(passwordEncoder.encode(resetPasswordRequestDTO.password()));
    userService.saveUser(user);
    return ResponseEntity.status(HttpStatus.OK)
        .body(new Result(true, HttpStatus.OK.value(), "Reset password successfully"));
  }

  @PostMapping("/subscribe")
  public ResponseEntity<Result> subscribeToTopic(
      @RequestBody @Valid NotificationSubscriptionRequest request) {
    try {
      // Topic name cannot contain '@'
      request.setTopicName(
          request.getTopicName().substring(0, request.getTopicName().indexOf("@")));
      fcmService.subscribeDeviceToTopic(request);
      return ResponseEntity.status(HttpStatus.OK)
          .body(
              new Result(
                  true, HttpStatus.OK.value(), "Device subscribed to the topic successfully."));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(
              new Result(
                  false,
                  HttpStatus.INTERNAL_SERVER_ERROR.value(),
                  "Failed to subscribe device to the topic."));
    }
  }

  @PostMapping("/unsubscribe")
  public ResponseEntity<Result> unSubscribeToTopic(
          @RequestBody @Valid NotificationSubscriptionRequest request) {
    try {
      // Topic name cannot contain '@'
      request.setTopicName(
              request.getTopicName().substring(0, request.getTopicName().indexOf("@")));
      fcmService.unsubscribeDeviceFromTopic(request);
      return ResponseEntity.status(HttpStatus.OK)
              .body(
                      new Result(
                              true, HttpStatus.OK.value(), "Device unsubscribed to the topic successfully."));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(
                      new Result(
                              false,
                              HttpStatus.INTERNAL_SERVER_ERROR.value(),
                              "Failed to unsubscribe device to the topic."));
    }
  }

  @GetMapping("/send-topic")
  public ResponseEntity<Result> sendTopic(@RequestParam("topic") String topic) {
    try {
      // Topic name cannot contain '@'
      TopicNotificationRequest topicNotificationRequestOfTraveler =
          TopicNotificationRequest.builder()
              .topicName(topic.substring(0, topic.indexOf("@")))
              .title("Test send to topic")
              .body(JsonUtils.convertObjectToJson(new NotificationSubscriptionRequest("hi", "hi")))
              .build();
      fcmService.sendPushNotificationToTopic(topicNotificationRequestOfTraveler);
      return ResponseEntity.status(HttpStatus.OK)
              .body(
                      new Result(
                              true, HttpStatus.OK.value(), "send to the topic successfully."));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(
                      new Result(
                              false,
                              HttpStatus.INTERNAL_SERVER_ERROR.value(),
                              "Failed to send to the topic."));
    }
  }

  @GetMapping("/send-device-token")
  public ResponseEntity<Result> sendDeviceToken(@RequestParam("deviceToken") String deviceToken) {
    try {
      // Topic name cannot contain '@'
      DeviceNotificationRequest deviceNotificationRequest =
              DeviceNotificationRequest.builder()
                      .deviceToken(deviceToken)
                      .title("Test send to device token")
                      .body(JsonUtils.convertObjectToJson(new NotificationSubscriptionRequest("hi", "hi")))
                      .build();
      fcmService.sendNotificationToDevice(deviceNotificationRequest);
      return ResponseEntity.status(HttpStatus.OK)
              .body(
                      new Result(
                              true, HttpStatus.OK.value(), "send to the device successfully."));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(
                      new Result(
                              false,
                              HttpStatus.INTERNAL_SERVER_ERROR.value(),
                              "Failed to send to the device."));
    }
  }
}
