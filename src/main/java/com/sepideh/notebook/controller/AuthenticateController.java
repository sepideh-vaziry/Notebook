package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.exeption.TokenInvalidException;
import com.sepideh.notebook.exeption.UsernameOrPasswordNotCorrectException;
import com.sepideh.notebook.model.JwtAuth;
import com.sepideh.notebook.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public GenericRestResponse<JwtAuth> login(@RequestBody JwtAuth auth) {

        authenticateCheck(auth.getUsername(), auth.getPassword());

        //Generate token and refresh token
        JwtAuth responseJwtAuth = new JwtAuth(
            null,
            null,
            jwtUtil.getToken(auth.getUsername()),
            jwtUtil.getRefreshToken(auth.getUsername())
        );

        return new GenericRestResponse<>(responseJwtAuth, "Success", HttpStatus.OK.value());
    }

    //******************************************************************************************************************
    @PostMapping("/refresh")
    public GenericRestResponse<JwtAuth> refresh(@RequestBody JwtAuth auth) {
        //Get username from refresh token
        String username = null;

        try {
            username = jwtUtil.getUsernameFromToken(auth.getRefresh());
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
        } catch (SignatureException e) {
            System.out.println("JWT signature does not match locally computed signature");
        }

        //Check token is valid or not
        if (username == null || jwtUtil.isTokenExpired(auth.getRefresh())) {
            throw new TokenInvalidException();
        }

        //Generate token and refresh token
        JwtAuth responseJwtAuth = new JwtAuth(
            null,
            null,
            jwtUtil.getToken(auth.getUsername()),
            jwtUtil.getRefreshToken(auth.getUsername())
        );

        return new GenericRestResponse<>(responseJwtAuth, "Success", HttpStatus.OK.value());
    }

    //******************************************************************************************************************
    private void authenticateCheck(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (BadCredentialsException | InternalAuthenticationServiceException | UsernameNotFoundException e) {
            throw new UsernameOrPasswordNotCorrectException();
        }
    }

}
