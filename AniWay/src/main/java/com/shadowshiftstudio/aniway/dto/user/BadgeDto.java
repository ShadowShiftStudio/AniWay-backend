package com.shadowshiftstudio.aniway.dto.user;

import com.shadowshiftstudio.aniway.entity.user.BadgeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto {
    private long id;
    private String name;
    private Date createdAt;
    public static BadgeDto toDto(BadgeEntity entity) {
        return BadgeDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
