package com.shadowshiftstudio.aniway.dto.user;

import com.shadowshiftstudio.aniway.entity.user.UserChapter;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private Sex sex;
    private int xp;
    private int pass_xp;
    private int balance;
    private Date createdAt;
    private int chapters;
    private int likes;
    private int commentsCount;
    private String avatarUrl;
    private String backgroundUrl;

    public static UserDto toDto(UserEntity entity) {
        return UserDto
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .sex(entity.getSex())
                .xp(entity.getXp())
                .pass_xp(entity.getPassXp())
                .balance(entity.getBalance())
                .createdAt(entity.getCreatedAt())
                .chapters(entity.getChaptersInfo()
                        .stream()
                        .map(UserChapter::isRead)
                        .toList()
                        .size()
                )
                .likes(entity.getChaptersInfo()
                        .stream()
                        .map(UserChapter::isLiked)
                        .toList()
                        .size()
                )
                .commentsCount(entity.getComments().size())
                .avatarUrl(entity.getAvatarUrl())
                .backgroundUrl(entity.getBackgroundUrl())
                .build();
    }
}
