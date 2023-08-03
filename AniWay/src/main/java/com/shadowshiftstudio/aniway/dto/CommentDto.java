package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto  {
    private String text;
    private Date createdAt;
    private Date updatedAt;

    public static CommentDto toDto(CommentEntity entity) {
        return CommentDto
                .builder()
                .text(entity.getText())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
