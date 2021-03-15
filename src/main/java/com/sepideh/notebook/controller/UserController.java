package com.sepideh.notebook.controller;

import com.sepideh.notebook.domain.User;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<GenericRestResponse<List<User>>> getAllUser(
            @RequestParam("pageSize") int pageSize,
            @RequestParam("pageNumber") int pageNumber
    ) {
        Page<User> page = userService.getAllUser(PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(
                new GenericRestResponse<>(
                    page.getContent(),
                    HttpStatus.OK.value(),
                    pageSize,
                    pageNumber,
                    page.getTotalPages(),
                    page.getTotalElements()
                ),
                HttpStatus.OK
        );
    }

}
