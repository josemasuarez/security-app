package com.uai.lppa.security.controller;

import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.service.PrivilegeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/privileges")
@AllArgsConstructor
@Slf4j
public class PrivilegeController {

    private PrivilegeService privilegeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Privilege create(final String description) {
        log.info("Creating privilege with description: {}", description);
        return privilegeService.create(description);
    }
}
