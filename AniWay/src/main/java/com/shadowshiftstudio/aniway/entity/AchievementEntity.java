package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.entity.user.UserAchievement;
import com.shadowshiftstudio.aniway.enums.AchievementType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="achievements")
public class AchievementEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name="avatar_url")
    private String avatarUrl;

    private String header;

    private String text;

    @Enumerated(STRING)
    private AchievementType type;

    private int condition;
    @OneToMany(mappedBy = "achievement")
    private Set<UserAchievement> achievementsInfo;
}