package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.ChapterCommentsEntity;
import com.shadowshiftstudio.aniway.entity.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.TeamEntity;
import com.shadowshiftstudio.aniway.entity.TitleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ChapterDto {
    private long id;
    private String name;
    private int number;
    private int volume;
    private int views;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ChapterDto toDto(ChapterEntity entity) {
        return ChapterDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .number(entity.getNumber())
                .volume(entity.getVolume())
                .views(entity.getViews())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
