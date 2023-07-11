package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.*;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

import java.util.Set;

import static jakarta.persistence.EnumType.STRING;

@NoArgsConstructor
public class TitleDto {
    private long id;
    private String name;
    private String originalName;
    private int year;
    private String description;
    private TitleStatus status;
    private TitleType type;
    private int views;

    public static TitleDto toDto(TitleEntity entity) {
        TitleDto dto = new TitleDto();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.originalName = entity.getOriginalName();
        dto.year = entity.getYear();
        dto.description = entity.getDescription();
        dto.status = entity.getStatus();
        dto.type = entity.getType();
        dto.views = entity.getViews();

        return dto;
    }
}
