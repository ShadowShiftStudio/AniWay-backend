package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.BadgeEntity;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class BadgeDto {
    private long id;
    private String name;
    private LocalDateTime createdAt;

    public static BadgeDto toDto(BadgeEntity entity) {
        BadgeDto dto = new BadgeDto();

        dto.id = entity.getId();
        dto.name = entity.getName();
        dto.createdAt = entity.getCreatedAt();

        return dto;
    }
}
