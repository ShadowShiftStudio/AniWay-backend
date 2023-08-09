package com.shadowshiftstudio.aniway.entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="achievments")
public class AchievmentEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String header;

    @Column(name="avatar_url")
    private String avatarUrl;

    private String text;

    @ManyToMany(mappedBy = "achievments")
    private Set<UserEntity> users;
}
