package com.shadowshiftstudio.aniway.repository.user;

import com.shadowshiftstudio.aniway.entity.user.UserAchievement;
import com.shadowshiftstudio.aniway.entity.user.keys.UserAchievementKey;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.enums.AchievementType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, UserAchievementKey> {
    List<UserAchievement> findUserAchievementsByUserAndReceived(UserEntity user, boolean received);
    List<UserAchievement> findUserAchievementsByUserAndReceivedAndAchievement_Type(UserEntity user, boolean received, AchievementType achievementType);
}
