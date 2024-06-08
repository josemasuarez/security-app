package com.uai.lppa.security.repository;

import com.uai.lppa.security.model.RefreshToken;
import com.uai.lppa.security.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
