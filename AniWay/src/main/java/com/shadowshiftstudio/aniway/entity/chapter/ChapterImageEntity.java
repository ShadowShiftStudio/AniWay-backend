package com.shadowshiftstudio.aniway.entity.chapter;

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
@Table(name="chapter_images")
public class ChapterImageEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="chapter_id")
    private ChapterEntity imageChapter;

    @Column(name="image_index")
    private int imageIndex;

    private String url;
}
