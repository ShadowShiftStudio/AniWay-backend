package com.shadowshiftstudio.aniway.entity.chapter;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.TeamEntity;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="chapters")
public class ChapterEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "title_id", insertable = false, updatable = false)
    private TitleEntity title;

    @ManyToOne
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private TeamEntity team;

    @OneToMany(targetEntity = CommentEntity.class, cascade = ALL)
    @JoinColumn(referencedColumnName = "id")
    private Set<CommentEntity> comments;

    @Size(min = 3, message = "{validation.name.size.too_short}")
    @Size(max = 300, message = "{validation.name.size.too_long}")
    private String name;

    private int number;
    private int volume;
    private int views;

    @Column(name="number_of_pages")
    private int numberOfPages;

    @OneToMany(mappedBy = "chapter")
    private Set<ChapterImageEntity> images;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;
}
