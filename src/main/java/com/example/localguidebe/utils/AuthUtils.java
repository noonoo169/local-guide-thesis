package com.example.localguidebe.utils;

import com.example.localguidebe.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtils {

    public String generateAccessToken(String email, String password,AuthenticationManager authenticationManager,JwtTokenProvider jwtTokenProvider){

        try {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtTokenProvider.generateToken(email);
        return token;
    } catch (Exception e) {
        return "token was not created successfully";
    }}

}