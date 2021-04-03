package com.sepideh.notebook.service;

import com.sepideh.notebook.model.User;
import com.sepideh.notebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor *****************************************************************************************************
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Override methods ************************************************************************************************
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Optional<User> user = userRepository.findByUsername(username);

            if (!user.isPresent()) {
                throw new UsernameNotFoundException("No user found with username: " + username);
            }

            return user.get();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
