package com.example.localguidebe.controller;

import com.example.localguidebe.dto.requestdto.UserAuthDTO;
import com.example.localguidebe.dto.responsedto.ResponseDTO;
import com.example.localguidebe.entity.User;
import com.example.localguidebe.enums.RolesEnum;
import com.example.localguidebe.security.jwt.JwtTokenProvider;
import com.example.localguidebe.service.RoleService;
import com.example.localguidebe.service.UserService;
import com.example.localguidebe.utils.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    private UserService userService;
    private RoleService roleService;
    @Autowired
    Gson gson = new Gson();
    @Autowired
     public void setUserService(UserService userService){
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService){
        this.roleService = roleService;
    }
    @Autowired
    public JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthenticationManager authenticationManager;
    AuthUtils utilsAuth = new AuthUtils();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping ("/register")

    public ResponseEntity<ResponseDTO> registerUser(@RequestBody UserAuthDTO userAuthDTO ){
        ResponseDTO response;
        if(userService.getAllUser().stream().anyMatch(user -> user.getEmail().equals(userAuthDTO.getEmail()))){
            response =  new ResponseDTO(HttpStatus.CONFLICT.value(), "Duplicate account", null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setEmail(userAuthDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userAuthDTO.getPassword()));
        user.getRoles().add(roleService.getRoleList().stream()
                .filter(role -> role.getName().equals(RolesEnum.TRAVELER))
                .findFirst().orElseThrow());
        userService.saveUser(user);
        userAuthDTO.setToken(utilsAuth.generateAccessToken(userAuthDTO.getEmail(), userAuthDTO.getPassword(), authenticationManager,jwtTokenProvider));

         response = new ResponseDTO(HttpStatus.OK.value(), "User registered successfully", userAuthDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
