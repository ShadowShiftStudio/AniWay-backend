package com.shadowshiftstudio.aniway.entity.user;

import com.shadowshiftstudio.aniway.entity.AchievementEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "badges")
public class BadgeEntity {
    @Id
    @GeneratedValue
    private long id;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 20, message = "{validation.name.size.too_long}")
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToMany(mappedBy = "badges")
    private Set<UserEntity> users;

    @OneToOne(mappedBy = "badge")
    private AchievementEntity achievement;

    public BadgeEntity addUser(UserEntity userEntity) {
        users.add(userEntity);
        return this;
    }
}
