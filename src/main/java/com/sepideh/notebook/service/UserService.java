package com.sepideh.notebook.service;

import com.sepideh.notebook.dto.user.CreateUserDto;
import com.sepideh.notebook.dto.user.SimpleUserDto;
import com.sepideh.notebook.mapper.UserMapper;
import com.sepideh.notebook.model.User;
import com.sepideh.notebook.enums.Role;
import com.sepideh.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    // Constructor *****************************************************************************************************
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    //******************************************************************************************************************
    public User registerUser(CreateUserDto createUserDto) {
        User user = userMapper.toModel(createUserDto);
        //Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Set role for user
        List<Role> roles = new ArrayList<>(1);
        roles.add(Role.USER);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    //******************************************************************************************************************
    public User updateUser(SimpleUserDto userDto) throws DataAccessException {
        User user = findById(userDto.getId());

        return userRepository.save(userMapper.update(userDto, user));
    }

    //******************************************************************************************************************
    public User findById(long id) throws DataAccessException {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public User findByUsername(String username) throws DataAccessException {
        return userRepository.findByUsername(username);
    }

    //******************************************************************************************************************
    public boolean delete(long id) throws DataAccessException {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);

        return true;
    }

    //******************************************************************************************************************
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
