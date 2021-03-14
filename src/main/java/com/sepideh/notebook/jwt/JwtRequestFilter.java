package com.sepideh.notebook.jwt;

import com.sepideh.notebook.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final static String KEY_HEADER_TOKEN = "Authorization";

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // Constructor *****************************************************************************************************
    public JwtRequestFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    //******************************************************************************************************************
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader(KEY_HEADER_TOKEN);

    }

}
