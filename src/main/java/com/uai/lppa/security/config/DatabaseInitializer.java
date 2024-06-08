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
        privilegeRepository.save(new Privilege(null, "CREATE_USER", null));
        privilegeRepository.save(new Privilege(null, "EDIT_USER", null));
        privilegeRepository.save(new Privilege(null, "DELETE_USER", null));
        privilegeRepository.save(new Privilege(null, "ADD_PRIVILEGE", null));
        privilegeRepository.save(new Privilege(null, "ADMIN", null));

    }
}
