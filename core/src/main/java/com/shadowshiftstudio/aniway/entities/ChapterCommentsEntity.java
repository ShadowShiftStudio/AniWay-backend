package com.shadowshiftstudio.aniway.entities;

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
@Table(name="chapter_comments")
public class ChapterCommentsEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="chapter_id", insertable = false, updatable = false)
    private ChapterEntity chapter;

    @OneToOne
    @JoinColumn(name="comment_id", referencedColumnName = "id")
    private CommentEntity comment;
}
