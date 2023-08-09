package com.shadowshiftstudio.aniway.dto.chapter;

import com.shadowshiftstudio.aniway.entity.chapter.GenreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private long id;
    private String name;

    public static GenreDto toDto(GenreEntity entity) {
        return GenreDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
