package com.sepideh.notebook.controller;

import com.sepideh.notebook.domain.User;
import com.sepideh.notebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    // Constructor *****************************************************************************************************
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"/register", "/register/"}, method = RequestMethod.POST)
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    //******************************************************************************************************************
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

}
