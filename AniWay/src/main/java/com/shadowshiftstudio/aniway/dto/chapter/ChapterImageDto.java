package com.shadowshiftstudio.aniway.dto.chapter;

import com.shadowshiftstudio.aniway.entity.chapter.ChapterImageEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterImageDto {
    private Long id;
    private String url;
    public static ChapterImageDto toDto(ChapterImageEntity entity) {
        return ChapterImageDto
                .builder()
                .id(entity.getId())
                .url(entity.getUrl())
                .build();
    }
}
