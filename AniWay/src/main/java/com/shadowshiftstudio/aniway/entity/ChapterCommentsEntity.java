package com.shadowshiftstudio.aniway.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
