package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.GenreEntity;
import com.shadowshiftstudio.aniway.entity.TitleEntity;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
public class GenreDto {
    private long id;
    private String name;

    public static GenreDto toDto(GenreEntity entity) {
        GenreDto dto = new GenreDto();

        dto.id = entity.getId();
        dto.name = entity.getName();

        return dto;
    }
}
