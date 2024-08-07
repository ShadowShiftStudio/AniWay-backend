package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.achievement.CreateAchievementRequest;
import com.shadowshiftstudio.aniway.dto.user.AchievementDto;
import com.shadowshiftstudio.aniway.entity.AchievementEntity;
import com.shadowshiftstudio.aniway.entity.user.BadgeEntity;
import com.shadowshiftstudio.aniway.entity.user.UserAchievement;
import com.shadowshiftstudio.aniway.entity.user.UserChapter;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.entity.user.keys.UserAchievementKey;
import com.shadowshiftstudio.aniway.exception.achievement.AchievementNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.BadgeNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserAchievementsNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.AchievementRepository;
import com.shadowshiftstudio.aniway.repository.user.BadgeRepository;
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

    @Autowired
    private BadgeRepository badgeRepository;

    public String createAchievement(CreateAchievementRequest request, MultipartFile avatar) throws AchievementNotFoundException, IOException, BadgeNotFoundException {
        String finalPath = imageService.uploadImage(avatar, "achievement_avatars/" + avatar.getOriginalFilename());
        Long badgeId = request.getBadgeId();

        AchievementEntity achievement = AchievementEntity
                .builder()
                .text(request.getText())
                .avatarUrl(finalPath)
                .header(request.getHeader())
                .condition(request.getCondition())
                .type(request.getType())
                .build();

        if (badgeId != 0)
            addBadge(achievement, request.getBadgeId());

        AchievementEntity finalAchievement = achievementRepository.save(achievement);
        addAchievementToUsers(finalAchievement);

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
            user.addAchievement(UserAchievement
                    .builder()
                    .achievement(achievement)
                    .user(user)
                    .received(false)
                    .id(UserAchievementKey
                            .builder()
                            .userId(user.getId())
                            .achievementId(achievement.getId())
                            .build()
                    )
                    .build());
        });

        userRepository.save(user);
    }

    private void addAchievementToUsers(AchievementEntity achievement) {
        userRepository.findAll().forEach((user) ->
                userRepository.save(user.addAchievement(UserAchievement
                        .builder()
                        .achievement(achievement)
                        .user(user)
                        .received(false)
                        .id(UserAchievementKey
                                .builder()
                                .achievementId(achievement.getId())
                                .userId(user.getId())
                                .build()
                        )
                        .build()))
        );
    }
    private void checkForReceived(UserEntity user) {
        userAchievementRepository
                .findUserAchievementsByUserAndReceived(user, false).forEach(this::checkCondition);
    }
    private void addBadge(AchievementEntity achievement, Long badgeId) throws BadgeNotFoundException {
        BadgeEntity badge = badgeRepository
                .findById(badgeId)
                .orElseThrow(() -> new BadgeNotFoundException("Badge not found"));

        badge.setAchievement(achievement);
        achievement.setBadge(badge);
    }
    private void checkCondition(UserAchievement userAchievement) {
        int distance = switch (userAchievement.getAchievement().getType()) {
            case CHAPTERS -> getChaptersDistance(userAchievement);
            case LIKES -> getLikesDistance(userAchievement);
            case LVL -> getLvlDistance(userAchievement);
            case COMMENTS -> getCommentsDistance(userAchievement);
        };

        if (userAchievement.getAchievement().getCondition() <= distance) {
            userAchievement.setReceived(true);
            findRelatedBadge(userAchievement);
        }
    }

    private void findRelatedBadge(UserAchievement userAchievement) {
        BadgeEntity badge = userAchievement.getAchievement().getBadge();
        UserEntity user = userAchievement.getUser();

        if (badge == null)
            return;

        userRepository.save(user.addBadge(badge));
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
