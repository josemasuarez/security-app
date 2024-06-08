package com.uai.lppa.security.service;

import com.uai.lppa.security.model.CustomUserDetails;
import com.uai.lppa.security.model.User;
import com.uai.lppa.security.service.UserService;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }

    public boolean hasPrivilege(Authentication authentication, String privilege) {
        final CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(privilege)
                        || authority.getAuthority().equals("ADMIN"));
    }
}
