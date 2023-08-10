package com.shadowshiftstudio.aniway.entity.user.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class UserAchievementKey implements Serializable {
    @Column(name="user_id")
    private Long userId;

    @Column(name="achievement_id")
    private Long achievementId;
}
