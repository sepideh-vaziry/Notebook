package com.sepideh.notebook.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class JwtUtil {

    private final static String SECRET = "Va#Z*yxNdGN986D";

    //******************************************************************************************************************
    /**
     * This method gets a username and generate a jwt token with an expiration date of one month.
     * @param username
     * @return Jwt token
     */
    public String generateToken(String username) {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 1);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    //******************************************************************************************************************
//    public String getUsernameFromToken(String token) {
//
//    }

}
