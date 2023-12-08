package com.chernomurov.effectivemobile.test.task.management.system.custom.security.filter;

import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import com.chernomurov.effectivemobile.test.task.management.system.repository.TokenRepository;
import com.chernomurov.effectivemobile.test.task.management.system.util.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            String tokenValue = authenticationHeader.substring("Bearer ".length());
            String userEmail = JwtUtils.getEmail(tokenValue);
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = (User) userDetailsService.loadUserByUsername(userEmail);

                boolean isTokenIsNonExpiredAndNonRevoked = tokenRepository
                        .findByValue(tokenValue)
                        .map(token -> !token.isExpired() && !token.isRevoked()).orElse(false);
                if (JwtUtils.isUserPresentedInTokenIsValid(tokenValue, userDetailsService.loadUserByUsername(userEmail)) &&
                        isTokenIsNonExpiredAndNonRevoked) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user.getPrincipal(),
                            user.getCredentials(),
                            user.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
