package com.sepideh.notebook.service;

import com.sepideh.notebook.domain.User;
import com.sepideh.notebook.enums.Role;
import com.sepideh.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor *****************************************************************************************************
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //******************************************************************************************************************
    public User registerUser(User user) {
        //Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Set role for user
        List<Role> roles = new ArrayList<>(1);
        roles.add(Role.USER);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    //******************************************************************************************************************
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
