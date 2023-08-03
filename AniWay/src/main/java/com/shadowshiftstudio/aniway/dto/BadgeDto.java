package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.BadgeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto {
    private long id;
    private String name;
    private LocalDateTime createdAt;

    public static BadgeDto toDto(BadgeEntity entity) {
        return BadgeDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
