package com.shadowshiftstudio.aniway.entity.chapter;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.team.TeamEntity;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.UserChapter;
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
@Table(name = "chapters")
public class ChapterEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private TitleEntity title;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 300, message = "{validation.name.size.too_long}")
    private String name;

    private int number;
    private int volume;
    private int views;

    @Column(name = "number_of_pages")
    private int numberOfPages;

    @OneToMany(mappedBy = "commentsChapter")
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "imageChapter")
    private Set<ChapterImageEntity> images;

    @OneToMany(mappedBy = "chapter")
    private Set<UserChapter> chaptersInfo;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    public ChapterEntity addUserChapter(UserChapter userChapter) {
        chaptersInfo.add(userChapter);
        userChapter.setChapter(this);
        return this;
    }
}
