package com.uai.lppa.security.controller;

import com.uai.lppa.security.controller.request.PrivilegeRequest;
import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.service.PrivilegeService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Privilege create(@RequestBody final String description) {
        log.info("Creating privilege with description: {}", description);
        return privilegeService.create(description);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Privilege> getAll() {
        log.info("Getting all privileges");
        return privilegeService.getAll();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestBody final PrivilegeRequest request) {
        log.info("Deleting privilege with description: {}", request.getDescription());
        privilegeService.delete(request.getDescription());
    }
}
