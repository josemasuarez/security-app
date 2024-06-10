package com.uai.lppa.security.config;

import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.repository.PrivilegeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DatabaseInitializer {

    private final PrivilegeRepository privilegeRepository;

    @PostConstruct
    public void init() {
        createPrivilegeIfNotExists("ADMIN");
        createPrivilegeIfNotExists("VIEWER");
        createPrivilegeIfNotExists("EDITOR");
    }

    private void createPrivilegeIfNotExists(final String privilegeName) {
        privilegeRepository.getPrivilegeByDescription(privilegeName).orElseGet(() -> {
            Privilege privilege = new Privilege(null, privilegeName, null);
            return privilegeRepository.save(privilege);
        });
    }
}
