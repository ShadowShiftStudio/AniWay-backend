package com.shadowshiftstudio.aniway.dto.user;

import com.shadowshiftstudio.aniway.entity.AchievementEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievementDto {
    private String header;
    private String avatarUrl;
    private String text;
    public static AchievementDto toDto(AchievementEntity entity) {
        return AchievementDto
                .builder()
                .header(entity.getHeader())
                .avatarUrl(entity.getAvatarUrl())
                .text(entity.getText())
                .build();
    }
}
