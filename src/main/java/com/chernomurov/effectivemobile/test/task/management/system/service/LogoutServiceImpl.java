package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.entity.Token;
import com.chernomurov.effectivemobile.test.task.management.system.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            String tokenValue = authenticationHeader.substring("Bearer ".length());
            Optional<Token> token = tokenRepository.findByValue(tokenValue);
            token.ifPresent(t -> {
                t.setExpired(true);
                t.setRevoked(true);
                tokenRepository.save(t);
                SecurityContextHolder.clearContext();
            });
        }
    }
}
