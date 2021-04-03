package com.sepideh.notebook.controller;

import com.sepideh.notebook.dto.response.GenericRestResponse;
import com.sepideh.notebook.model.JwtAuth;
import com.sepideh.notebook.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
    public ResponseEntity<GenericRestResponse<JwtAuth>> login(@RequestBody JwtAuth auth) {

        try {
            authenticateCheck(auth.getUsername(), auth.getPassword());
        }
        catch (Exception e) {
            System.out.println(e);

            return new ResponseEntity<>(
                new GenericRestResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Username or password is correct",
                    null
                ),
                HttpStatus.OK
            );
        }

        //Generate token and refresh token
        JwtAuth responseJwtAuth = new JwtAuth(
            null,
            null,
            jwtUtil.getToken(auth.getUsername()),
            jwtUtil.getRefreshToken(auth.getUsername())
        );

        return new ResponseEntity<>(
            new GenericRestResponse<>(responseJwtAuth, "Success", HttpStatus.OK.value()),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    @PostMapping("/refresh")
    public ResponseEntity<GenericRestResponse<JwtAuth>> refresh(@RequestBody JwtAuth auth) {
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
            return new ResponseEntity<>(
                new GenericRestResponse<>(
                    HttpStatus.UNAUTHORIZED.value(),
                    "Unauthorized",
                    "Unauthorized"
                ),
                HttpStatus.UNAUTHORIZED
            );
        }

        //Generate token and refresh token
        JwtAuth responseJwtAuth = new JwtAuth(
            null,
            null,
            jwtUtil.getToken(auth.getUsername()),
            jwtUtil.getRefreshToken(auth.getUsername())
        );

        return new ResponseEntity<>(
            new GenericRestResponse<>(responseJwtAuth, "Success", HttpStatus.OK.value()),
            HttpStatus.OK
        );
    }

    //******************************************************************************************************************
    private void authenticateCheck(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

}
