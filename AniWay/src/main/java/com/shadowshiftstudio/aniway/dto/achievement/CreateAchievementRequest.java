package com.shadowshiftstudio.aniway.dto.achievement;

import com.shadowshiftstudio.aniway.enums.AchievementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAchievementRequest {
    private String header;
    private String avatarUrl;
    private String text;
    private AchievementType type;
    private int condition;
}
