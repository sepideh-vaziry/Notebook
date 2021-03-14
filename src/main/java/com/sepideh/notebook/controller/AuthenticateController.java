package com.sepideh.notebook.controller;

import com.sepideh.notebook.jwt.JwtAuth;
import com.sepideh.notebook.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticateController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    // Constructor *****************************************************************************************************
    @Autowired
    public AuthenticateController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    //******************************************************************************************************************
    @PostMapping()
    public ResponseEntity<JwtAuth> login(@RequestBody JwtAuth auth) {

        try {
            authenticateCheck(auth.getUsername(), auth.getPassword());
        }
        catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JwtAuth responseJwtAuth = new JwtAuth(jwtUtil.generateToken(auth.getUsername()));
        return new ResponseEntity<>(responseJwtAuth, HttpStatus.OK);
    }

    //******************************************************************************************************************
    private void authenticateCheck(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
