package com.shadowshiftstudio.aniway.repository.user;

import com.shadowshiftstudio.aniway.entity.user.BadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<BadgeEntity, Long> {
    Optional<BadgeEntity> findByName(String name);
}
