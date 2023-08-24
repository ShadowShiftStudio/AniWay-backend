package com.shadowshiftstudio.aniway.dto.chapter;

import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChapterDto {
    private Long id;
    private String name;
    private int number;
    private int volume;
    private int views;
    private int numberOfPages;
    private Date createdAt;
    private Date updatedAt;

    public static ChapterDto toDto(ChapterEntity entity) {
        return ChapterDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(entity.getNumber())
                .volume(entity.getVolume())
                .numberOfPages(entity.getNumberOfPages())
                .views(entity.getViews())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
