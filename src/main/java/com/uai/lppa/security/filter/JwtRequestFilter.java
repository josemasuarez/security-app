package com.uai.lppa.security.filter;

import com.uai.lppa.security.model.CustomUserDetails;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String jwt = authorizationHeader.substring(7);
            log.debug("Extracted JWT: {}", jwt);

            try {
                final Claims claims = jwtUtil.extractAllClaims(jwt);
                final String username = claims.getSubject();
                final Long userId = claims.get("id", Long.class);

                log.info("JWT Token extracted. Username: {}, User ID: {}", username, userId);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    processAuthentication(request, jwt, username);
                }
            } catch (Exception e) {
                log.error("Failed to parse JWT: {}", e.getMessage());
            }
        } else {
            log.info("No valid Authorization header found.");
        }

        chain.doFilter(request, response);
    }

    private void processAuthentication(HttpServletRequest request, String jwt, String username) {
        final CustomUserDetails customUserDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(username);
        log.debug("Loaded UserDetails: {}", customUserDetails);

        if (Boolean.TRUE.equals(jwtUtil.validateToken(jwt, customUserDetails))) {
            setAuthenticationContext(request, customUserDetails);
            log.info("Authentication successful. User: {}", username);
        } else {
            log.warn("Invalid JWT token for user: {}", username);
        }
    }

    private void setAuthenticationContext(HttpServletRequest request, CustomUserDetails customUserDetails) {
        final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
    }
}