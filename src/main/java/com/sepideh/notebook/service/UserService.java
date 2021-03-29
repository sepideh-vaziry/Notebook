package com.sepideh.notebook.service;

import com.sepideh.notebook.dto.user.CreateUserDto;
import com.sepideh.notebook.dto.user.SimpleUserDto;
import com.sepideh.notebook.mapper.UserMapper;
import com.sepideh.notebook.model.User;
import com.sepideh.notebook.enums.Role;
import com.sepideh.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final static String USER_CACHE_VALUE = "UserCache";
    private final static String HASH_NAME_FOR_USER_NAMES = "HashNameForUsername";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, Long, String> hashOperations;

    // Constructor *****************************************************************************************************
    @Autowired
    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        UserMapper userMapper,
        RedisTemplate<String, String> redisTemplate
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();

        //Sample for put, get and delete from redis
//        hashOperations.put(HASH_NAME_FOR_USER_NAMES, user.getId(), user.getUsername());
//        hashOperations.get(HASH_NAME_FOR_USER_NAMES, user.getId());
//        hashOperations.delete(HASH_NAME_FOR_USER_NAMES, user.getId());
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
    @CachePut(value = USER_CACHE_VALUE, key = "'UserId='.concat(#userDto.id)")
    public User updateUser(SimpleUserDto userDto) throws DataAccessException {
        User user = findById(userDto.getId());

        return userRepository.save(userMapper.update(userDto, user));
    }

    //******************************************************************************************************************
    @Cacheable(value = USER_CACHE_VALUE, key = "'UserId='.concat(#id)")
    public User findById(long id) throws DataAccessException {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    //******************************************************************************************************************
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    //******************************************************************************************************************
    @CacheEvict(value = USER_CACHE_VALUE, key = "'UserId='.concat(#id)")
    public boolean delete(long id) throws DataAccessException {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);

        return true;
    }

}
