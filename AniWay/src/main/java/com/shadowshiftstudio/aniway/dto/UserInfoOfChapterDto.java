package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.UserInfoOfChaptersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoOfChapterDto {
    private long id;
    private boolean isLiked;
    private boolean isReaded;

    public static UserInfoOfChapterDto toDto(UserInfoOfChaptersEntity entity) {
        return UserInfoOfChapterDto
                .builder()
                .id(entity.getId())
                .isLiked(entity.isLiked())
                .isReaded(entity.isReaded())
                .build();
    }
}
