package com.uai.lppa.security.repository;

import com.uai.lppa.security.model.Privilege;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    void deleteByDescription(final String description);

    Optional<Privilege> getPrivilegeByDescription(final String description);
}
