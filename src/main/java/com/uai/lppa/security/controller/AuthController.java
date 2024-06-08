package com.uai.lppa.security.controller;

import com.uai.lppa.security.filter.JwtUtil;
import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.model.RefreshToken;
import com.uai.lppa.security.model.User;
import com.uai.lppa.security.controller.request.AuthenticationRequest;
import com.uai.lppa.security.controller.request.UserRequest;
import com.uai.lppa.security.service.RefreshTokenService;
import com.uai.lppa.security.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    private UserDetailsService userDetailsService;

    private UserService userService;

    private RefreshTokenService refreshTokenService;

    @PostMapping("/authenticate")
    public Map<String, String> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Authenticating user: {}", authenticationRequest.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
        );

        final User user = userService.getUserByUserName(authenticationRequest.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        Set<String> privileges = user.getPrivileges().stream()
                .map(Privilege::getDescription)
                .collect(Collectors.toSet());

        final String jwt = jwtUtil.generateToken(userDetails, privileges, user.getId());


        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        Map<String, String> tokens = new HashMap<>();
        tokens.put("jwt", jwt);
        tokens.put("refreshToken", refreshToken.getToken());

        return tokens;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequest user) {
        return new ResponseEntity<>(userService.registerNewUser(user), HttpStatus.CREATED);
    }
}
