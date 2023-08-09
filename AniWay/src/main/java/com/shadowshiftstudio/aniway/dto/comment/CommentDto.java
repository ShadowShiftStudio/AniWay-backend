package com.shadowshiftstudio.aniway.dto.comment;

import com.shadowshiftstudio.aniway.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto  {
    private String text;
    private String username;
    private String titleName;
    private String avatarUrl;
    private Date createdAt;

    public static CommentDto toDto(CommentEntity entity) {
        return CommentDto
                .builder()
                .text(entity.getText())
                .createdAt(entity.getCreatedAt())
                .username(entity.getAuthor().getUsername())
                .titleName(entity.getTitle().getName())
                .avatarUrl(entity.getAuthor().getAvatarUrl())
                .build();
    }
}
