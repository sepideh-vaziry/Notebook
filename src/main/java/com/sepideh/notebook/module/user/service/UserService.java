package com.sepideh.notebook.module.user.service;

import com.sepideh.notebook.module.user.model.User;
import com.sepideh.notebook.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor *****************************************************************************************************
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //******************************************************************************************************************
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    //******************************************************************************************************************
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}
