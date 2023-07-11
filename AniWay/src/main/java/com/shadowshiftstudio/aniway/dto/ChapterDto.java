package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.ChapterCommentsEntity;
import com.shadowshiftstudio.aniway.entity.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.TeamEntity;
import com.shadowshiftstudio.aniway.entity.TitleEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

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
        ChapterDto dto = new ChapterDto();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.number = entity.getNumber();
        dto.volume = entity.getVolume();
        dto.views = entity.getViews();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        return dto;
    }
}
