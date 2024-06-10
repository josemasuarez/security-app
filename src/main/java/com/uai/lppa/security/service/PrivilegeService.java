package com.uai.lppa.security.service;

import com.uai.lppa.security.exception.BadRequestException;
import com.uai.lppa.security.model.Privilege;
import com.uai.lppa.security.repository.PrivilegeRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PrivilegeService {

    private PrivilegeRepository privilegeRepository;

    public Privilege create(final String description) {
        return privilegeRepository.save(Privilege.builder().description(description).build());
    }

    public List<Privilege> getAll() {
        return privilegeRepository.findAll();
    }

    @Transactional
    public void delete(final String description) {
        if (privilegeRepository.getPrivilegeByDescription(description).isEmpty()) {
            throw new BadRequestException("Privilege not found");
        }
        if(description.equals(Privilege.ADMIN)){
            throw new BadRequestException("Cannot delete ADMIN privilege");
        }

        privilegeRepository.deleteByDescription(description);
    }
}
