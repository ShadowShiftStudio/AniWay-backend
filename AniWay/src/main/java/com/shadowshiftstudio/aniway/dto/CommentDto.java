package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import com.shadowshiftstudio.aniway.entity.UserEntity;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class CommentDto {
    private long id;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();

        dto.id = entity.getId();
        dto.text = entity.getText();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        return dto;
    }
}
