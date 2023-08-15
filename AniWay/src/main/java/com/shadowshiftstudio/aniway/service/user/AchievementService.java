package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.achievement.CreateAchievementRequest;
import com.shadowshiftstudio.aniway.dto.user.AchievementDto;
import com.shadowshiftstudio.aniway.entity.AchievementEntity;
import com.shadowshiftstudio.aniway.entity.user.UserAchievement;
import com.shadowshiftstudio.aniway.entity.user.UserChapter;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.entity.user.keys.UserAchievementKey;
import com.shadowshiftstudio.aniway.enums.AchievementType;
import com.shadowshiftstudio.aniway.exception.achievement.AchievementNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserAchievementsNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.AchievementRepository;
import com.shadowshiftstudio.aniway.repository.user.UserAchievementRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AchievementService {
    @Autowired
    private AchievementRepository achievementRepository;

    @Autowired
    private UserAchievementRepository userAchievementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    public String createAchievement(CreateAchievementRequest request, MultipartFile avatar) throws AchievementNotFoundException, IOException {
        String finalPath = imageService.uploadImage(avatar, "achievement_avatars/" + avatar.getOriginalFilename());

        AchievementEntity achievement = AchievementEntity
                .builder()
                .text(request.getText())
                .avatarUrl(finalPath)
                .header(request.getHeader())
                .condition(request.getCondition())
                .type(request.getType())
                .build();

        achievementRepository.save(achievement);

        AchievementEntity finalAchievement = achievementRepository.findByHeader(request.getHeader())
                .orElseThrow(() -> new AchievementNotFoundException("Achievement not found"));

        userRepository.findAll().forEach((user) ->
            userAchievementRepository.save(UserAchievement
                    .builder()
                    .achievement(achievement)
                    .user(user)
                    .id(UserAchievementKey
                            .builder()
                            .achievementId(finalAchievement.getId())
                            .userId(user.getId())
                            .build()
                    )
                    .received(false)
                    .build())
        );

        return "Achievement created successfully";
    }

    public List<AchievementDto> getUserAchievements(String username, boolean received) throws UserNotFoundException, UserAchievementsNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        checkForReceived(user);

        List<AchievementDto> achievements =
                userAchievementRepository
                        .findUserAchievementsByUserAndReceived(
                                user,
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

    public void initUserAchievements(String username) throws UserNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        achievementRepository.findAll().forEach((achievement) -> {
            userAchievementRepository.save(UserAchievement
                    .builder()
                            .achievement(achievement)
                            .user(user)
                            .id(UserAchievementKey
                                    .builder()
                                    .userId(user.getId())
                                    .achievementId(achievement.getId())
                                    .build()
                            )
                            .received(false)
                    .build());
        });
    }

    private void checkForReceived(UserEntity user) {
        userAchievementRepository
                .findUserAchievementsByUserAndReceived(user, false).forEach(this::checkCondition);
    }

    private void checkCondition(UserAchievement userAchievement) {
        int distance = 0;
        switch (userAchievement.getAchievement().getType()) {
            case CHAPTERS:
                distance = getChaptersDistance(userAchievement);
                break;
            case LIKES:
                distance = getLikesDistance(userAchievement);
                break;
            case LVL:
                distance = getLvlDistance(userAchievement);
                break;
            case COMMENTS:
                distance = getCommentsDistance(userAchievement);
                break;
        }

        if (userAchievement.getAchievement().getCondition() <= distance) {
            userAchievement.setReceived(true);
            userAchievementRepository.save(userAchievement);
        }
    }

    private int getCommentsDistance(UserAchievement userAchievement) {
        return userAchievement
                .getUser()
                .getComments()
                .size();
    }

    private int getLikesDistance(UserAchievement userAchievement) {
        return userAchievement
                .getUser()
                .getChaptersInfo()
                .stream()
                .map(UserChapter::isLiked)
                .toList()
                .size();
    }

    private int getLvlDistance(UserAchievement userAchievement) {
        return userAchievement
                .getUser()
                .getLvl();
    }

    private int getChaptersDistance(UserAchievement userAchievement) {
        return userAchievement
                .getUser()
                .getChaptersInfo()
                .stream()
                .map(UserChapter::isRead)
                .toList()
                .size();
    }
}
