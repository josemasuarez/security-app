package com.uai.lppa.security.controller.validator;

import com.uai.lppa.security.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class IdValidator {

    public boolean validate(final Long id, final Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return customUserDetails.getId().equals(id);
    }
}
