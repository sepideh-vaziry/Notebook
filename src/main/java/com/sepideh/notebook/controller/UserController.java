package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.user.CreateUserDto;
import com.sepideh.notebook.dto.user.SimpleUserDto;
import com.sepideh.notebook.model.User;
import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.mapper.UserMapper;
import com.sepideh.notebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    // Constructor *****************************************************************************************************
    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    //***********************************************************************p*******************************************
    @RequestMapping(value = {"/register", "/register/"}, method = RequestMethod.POST)
    public GenericRestResponse<SimpleUserDto> registerUser(
        @RequestBody @Valid CreateUserDto createUserDto
    ) {
        return new GenericRestResponse<>(
            userMapper.toDto(userService.registerUser(createUserDto)),
            "Success create",
            HttpStatus.CREATED.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public GenericRestResponse<SimpleUserDto> update(
        @RequestBody @Valid SimpleUserDto simpleUserDto
    ) {
        return new GenericRestResponse<>(
            userMapper.toDto(userService.updateUser(simpleUserDto)),
            "Success update",
            HttpStatus.OK.value()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public GenericRestResponse<List<SimpleUserDto>> getAllUser(
        @RequestParam("pageSize") int pageSize,
        @RequestParam("pageNumber") int pageNumber
    ) {
        Page<User> page = userService.getAllUser(PageRequest.of(pageNumber, pageSize));

        return new GenericRestResponse<>(
            userMapper.toDto(page.getContent()),
            HttpStatus.OK.value(),
            pageSize,
            pageNumber,
            page.getTotalPages(),
            page.getTotalElements()
        );
    }

    //******************************************************************************************************************
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public GenericRestResponse<Boolean> delete(
        @PathVariable("id") long id
    ) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (loggedInUser.getId() == id) {
            return new GenericRestResponse<>(
                userService.delete(id),
                "Delete successfully",
                HttpStatus.OK.value()
            );
        } else {
            return new GenericRestResponse<>(
                false,
                "Access Denied",
                HttpStatus.FORBIDDEN.value()
            );
        }

    }

}
