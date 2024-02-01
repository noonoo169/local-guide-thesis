package com.example.localguidebe.service.impl;

import com.example.localguidebe.entity.User;
import com.example.localguidebe.repository.UserRepository;
import com.example.localguidebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User saveUser(User user) {
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public List<User> getAllUser() {
       return this.userRepository.findAll();

    }
}
