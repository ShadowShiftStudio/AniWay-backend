package com.shadowshiftstudio.aniway.dto.user;

import com.shadowshiftstudio.aniway.entity.user.AchievmentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AchievmentDto {
    private String header;
    private String avatarUrl;
    private String text;

    public static AchievmentDto toDto(AchievmentEntity entity) {
        return AchievmentDto
                .builder()
                .header(entity.getHeader())
                .avatarUrl(entity.getAvatarUrl())
                .text(entity.getText())
                .build();
    }
}
