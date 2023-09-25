package com.notifyme.application.service;

import com.notifyme.application.model.User;
import com.notifyme.application.model.UserType;
import com.notifyme.application.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Slf4j
@Component
public class JWTService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.accessTokenValidityMS}")
    private String jwtExpirationMs;

    @Value("${jwt.cookieName}")
    private String jwtCookie;

    public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
        String jwt = generateTokenForUser(userPrincipal.getUser());
        return ResponseCookie.from(jwtCookie, jwt)
                .maxAge(60 * 60)
//              httpOnly(true) is called on the ResponseCookieBuilder to set the
//              HTTP-only flag for the cookie instance. When the browser receives
//              the Set-Cookie header with this cookie, it will only send the cookie
//              value in HTTP or HTTPS requests and prevent client-side scripts
//              from accessing the cookie's value.
                .httpOnly(true)
                .build();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("Something went wrong: {}", e.getMessage());
        }
        return false;
    }

    public String generateTokenForUser(User user) {
        return Jwts.builder().setSubject(user.getEmailAddress())
                .setIssuedAt(new Date())
                .claim("userId", user.getIID())
                .claim("userType", user.getType())
                .setExpiration(new Date(new Date().getTime() + Integer.parseInt(jwtExpirationMs)))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getUserId(String token) {
        Claims claims = new DefaultClaims();
        try {
            claims = Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            log.error("Something went wrong parsing token extracting UserID: {}", e.getMessage());
        }

        return Long.valueOf(claims.get("userId").toString());
    }

    public String getUserType(String token) {
        Claims claims = new DefaultClaims();
        try {
            claims = Jwts.parser().setSigningKey(jwtSecret)
                    .parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
            log.error("Something went wrong parsing token extracting UserID: {}", e.getMessage());
        }
        return claims.get("userType").toString();
    }
}
