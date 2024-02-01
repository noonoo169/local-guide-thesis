package com.example.localguidebe.service;

import com.example.localguidebe.entity.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUser();
}
