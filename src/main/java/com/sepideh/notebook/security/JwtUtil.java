package com.sepideh.notebook.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${project.jwt.secret}")
    private String secret;

    //******************************************************************************************************************
    /**
     * This method gets a username and generate a jwt token with an expiration date of one month.
     * @param username
     * @return Jwt token
     */
    public String getToken(String username) {
        return generateToken(username, 1);
    }

    //******************************************************************************************************************
    /**
     * This method gets a username and generate a jwt token with an expiration date of two month.
     * @param username
     * @return Jwt token
     */
    public String getRefreshToken(String username) {
        return generateToken(username, 2);
    }

    //******************************************************************************************************************
    private String generateToken(String username, int numberOfMonthsUntilExpiration) {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, numberOfMonthsUntilExpiration);

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(currentDate)
            .setExpiration(calendar.getTime())
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }

    //******************************************************************************************************************
    /**
     * Retrieve username from jwt token
     * @param token jwt token
     * @return username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //******************************************************************************************************************
    /**
     * check if the token has expired
     * @param token
     * @return true if token has been expired otherwise false
     */
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    //******************************************************************************************************************
    /**
     * Retrieve expiration date from jwt token
     * @param token Jwt token
     * @return token expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //******************************************************************************************************************
    /**
     * Retrieve {T} from claims
     */
    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //******************************************************************************************************************
    /**
     * Retrieve any information from token that requires the secret key
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

}
