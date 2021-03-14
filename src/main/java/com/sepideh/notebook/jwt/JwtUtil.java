package com.sepideh.notebook.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

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
     * @return
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
     * Retrieve any information from token we will need the secret key
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

}
