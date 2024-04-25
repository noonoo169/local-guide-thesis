package com.example.localguidebe.controller;

import com.example.localguidebe.converter.UserToLoginResponseDtoConverter;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.dto.requestdto.UserAuthDTO;
import com.example.localguidebe.dto.requestdto.LoginRequestDTO;
import com.example.localguidebe.dto.responsedto.ResponseDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.security.jwt.JwtTokenProvider;
import com.example.localguidebe.security.service.CustomUserDetails;
import com.example.localguidebe.service.RoleService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.system.StatusCode;
import com.example.localguidebe.utils.AuthUtils;
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
@CrossOrigin("*")
public class AuthController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService){
        this.roleService = roleService;
    }

    @Autowired
    public void setUserToLoginResponseDtoConverter(UserToLoginResponseDtoConverter userToLoginResponseDtoConverter) {
        this.userToLoginResponseDtoConverter = userToLoginResponseDtoConverter;
    }

    @Autowired
    public JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationManager authenticationManager;

    AuthUtils utilsAuth = new AuthUtils();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    UserToLoginResponseDtoConverter userToLoginResponseDtoConverter;



    @PostMapping ("/register")

    public ResponseEntity<Result> registerUser(@RequestBody UserAuthDTO userAuthDTO ){
        ResponseDTO response;
        if(userService.getAllUser().stream().anyMatch(user -> user.getEmail().equals(userAuthDTO.getEmail()))){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new Result(
                            true,
                            HttpStatus.CONFLICT.value(),
                            "Duplicate account")
            );
        }
        User user = new User();
        user.setEmail(userAuthDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userAuthDTO.getPassword()));
        user.getRoles().add(roleService.getRoleList().stream()
                .filter(role -> role.getName().equals(RolesEnum.TRAVELER))
                .findFirst().orElseThrow());
        userService.saveUser(user);
        String accessToken = utilsAuth.generateAccessToken(userAuthDTO.getEmail(), userAuthDTO.getPassword(), authenticationManager,jwtTokenProvider);
        return ResponseEntity.status(HttpStatus.OK).body(
                new Result(
                        true,
                        HttpStatus.OK.value(),
                        "User login successfully",
                        userToLoginResponseDtoConverter.convert(user, accessToken))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody LoginRequestDTO userLoginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            User user = userService.findUserByEmail(userLoginDTO.getEmail());
            String accessToken = jwtTokenProvider.generateToken(customUserDetails.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new Result(
                            true,
                            HttpStatus.OK.value(),
                            "User login successfully",
                            userToLoginResponseDtoConverter.convert(user, accessToken))
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new Result(
                    false,
                    HttpStatus.UNAUTHORIZED.value(),
                    "Email or password not match"));
        }
    }
}
