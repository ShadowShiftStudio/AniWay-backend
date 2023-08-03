package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.GenreEntity;
import com.shadowshiftstudio.aniway.entity.TitleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
