package com.shadowshiftstudio.aniway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="title_comments")
public class TitleCommentsEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="title_id", insertable = false, updatable = false)
    private ChapterEntity title;

    @OneToOne
    @JoinColumn(name="comment_id", referencedColumnName = "id")
    private CommentEntity comment;
}
