package com.shadowshiftstudio.aniway.repository;

import com.shadowshiftstudio.aniway.entity.EmailVerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationTokenEntity, Long> {
    Optional<EmailVerificationTokenEntity> findByToken(String token);
}
