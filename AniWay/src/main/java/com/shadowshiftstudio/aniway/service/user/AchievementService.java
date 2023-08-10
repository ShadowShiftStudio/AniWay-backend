package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.achievement.CreateAchievementRequest;
import com.shadowshiftstudio.aniway.dto.user.AchievementDto;
import com.shadowshiftstudio.aniway.entity.AchievementEntity;
import com.shadowshiftstudio.aniway.entity.user.UserAchievement;
import com.shadowshiftstudio.aniway.exception.user.UserAchievementsNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.AchievementRepository;
import com.shadowshiftstudio.aniway.repository.user.UserAchievementRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AchievementService {
    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Autowired
    private UserRepository userRepository;
    public String createAchievement(CreateAchievementRequest request) {
        achievementRepository.save(AchievementEntity
                        .builder()
                        .text(request.getText())
                        .avatarUrl(request.getAvatarUrl())
                        .header(request.getHeader())
                        .condition(request.getCondition())
                        .type(request.getType())
                        .build()
        );

        return "Achievement created successfully";
    }

    public List<AchievementDto> getUserAchievements(String username, boolean received) throws UserNotFoundException, UserAchievementsNotFoundException {
        List<AchievementDto> achievements =
                userAchievementRepository
                        .findUserAchievementsByUserAndReceived(
                                userRepository.findByUsername(username)
                                .orElseThrow(() -> new UserNotFoundException("User not found")),
                                received
                        )
                        .stream()
                        .map(UserAchievement::getAchievement)
                        .map(AchievementDto::toDto)
                        .toList();

        if (achievements.isEmpty())
            throw new UserAchievementsNotFoundException("User achievements not found");

        return achievements;
    }
}
