package com.shadowshiftstudio.aniway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
