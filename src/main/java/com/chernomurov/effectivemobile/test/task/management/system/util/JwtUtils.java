package com.chernomurov.effectivemobile.test.task.management.system.util;

import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TokenType;
import com.chernomurov.effectivemobile.test.task.management.system.entity.Token;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
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
import java.util.Map;
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

    public Token generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, expiration, TokenType.ACCESS_TOKEN);
    }

    public Token generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, refreshExpiration, TokenType.REFRESH_TOKEN);
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

    private Token generateToken(UserDetails userDetails, long tokenExpiration, TokenType tokenType) {
        Date expirationDate = new Date(System.currentTimeMillis() + tokenExpiration);
        String value = getTokenValue(userDetails, expirationDate, tokenType);

        return Token.builder()
                .value(value)
                .type(tokenType)
                .user((User) userDetails)
                .build();
    }

    private String getTokenValue(UserDetails userDetails, Date expirationDate, TokenType tokenType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", tokenType);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
