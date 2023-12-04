package com.chernomurov.effectivemobile.test.task.management.system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@UtilityClass
public class JwtUtils {

    private String signingKey;
    private long expiration;
    private long refreshExpiration;

    public void setSigningKey(String signingKey) {
        JwtUtils.signingKey = signingKey;
    }

    public void setExpiration(long expiration) {
        JwtUtils.expiration = expiration;
    }

    public void setRefreshExpiration(long refreshExpiration) {
        JwtUtils.refreshExpiration = refreshExpiration;
    }

    public String generateToken(UserDetails userDetails) {
        Date currentDate = new Date(System.currentTimeMillis());
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isUserPresentedInTokenIsValid(String token, UserDetails userDetails) {
        boolean isUsernameInUserDetailsAndInJwtMatches = userDetails.getUsername().equals(getEmail(token));
        boolean isTokenExpired = getClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
        return isUsernameInUserDetailsAndInJwtMatches && !isTokenExpired;
    }

    public String getEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public <T> T getClaim(String jwt, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsFromJwt(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromJwt(String jwt) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKey));
    }
}
