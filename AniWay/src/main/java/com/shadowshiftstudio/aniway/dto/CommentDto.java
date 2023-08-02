package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@Data
public class CommentDto  {
    private String text;
    private Date createdAt;
    private Date updatedAt;

    public static CommentDto toDto(CommentEntity entity) {
        CommentDto dto = new CommentDto();

        dto.text = entity.getText();
        dto.createdAt = entity.getCreatedAt();
        dto.updatedAt = entity.getUpdatedAt();

        return dto;
    }
}
