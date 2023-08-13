package com.shadowshiftstudio.aniway.repository.user;

import com.shadowshiftstudio.aniway.entity.AchievementEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<AchievementEntity, Long> {
    Optional<AchievementEntity> findByHeader(String header);
}
