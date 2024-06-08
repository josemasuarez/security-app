package com.uai.lppa.security.service;

import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.repository.PrivilegeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    public Privilege create(final String description) {
        return privilegeRepository.save(Privilege.builder().description(description).build());
    }
}
