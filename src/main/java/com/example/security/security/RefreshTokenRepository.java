package com.example.security.security;

import com.example.security.security.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    boolean existsByToken(String refreshToken);

    Optional<RefreshToken> findByToken(String refreshToken);

    @Query(value = "update refresh_token r set r.active = 0 where r.family = :uuid", nativeQuery = true)
    @Modifying
    @Transactional
    void updateAllInvalidToken(UUID uuid);
}
