package com.shadowshiftstudio.aniway.entity.team;

import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="teams")
public class TeamEntity {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserEntity owner;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 20, message = "{validation.name.size.too_long}")
    private String name;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 150, message = "{validation.name.size.too_long}")
    private String description;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="avatar_url")
    private String avatarUrl;

    @OneToMany(targetEntity = ChapterEntity.class, cascade = ALL)
    @JoinColumn(referencedColumnName = "id")
    private Set<ChapterEntity> chapters;

    @OneToMany(mappedBy = "team")
    private Set<UserTeam> userTeamsInfo;

    public TeamEntity addChapter(ChapterEntity chapter) {
        chapters.add(chapter);
        chapter.setTeam(this);
        return this;
    }

    public TeamEntity addUser(UserTeam user) {
        userTeamsInfo.add(user);
        user.setTeam(this);
        return this;
    }
}
