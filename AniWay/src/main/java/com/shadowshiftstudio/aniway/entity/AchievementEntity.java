package com.shadowshiftstudio.aniway.entity;

import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "user_achievements",
            joinColumns = @JoinColumn(name = "achievement_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<UserEntity> users;
}
