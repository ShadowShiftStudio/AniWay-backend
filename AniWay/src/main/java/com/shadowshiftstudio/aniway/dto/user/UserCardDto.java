package com.shadowshiftstudio.aniway.dto.user;


import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserCardDto {
    private Long id;
    private String username;
    private String avatarUrl;

    public static UserCardDto toDto(UserEntity entity) {
        return UserCardDto
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }
}
