package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.UserInfoOfChaptersEntity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserInfoOfChapterDto {
    private long id;
    private boolean isLiked;
    private boolean isReaded;

    public static UserInfoOfChapterDto toDto(UserInfoOfChaptersEntity entity) {
        UserInfoOfChapterDto dto = new UserInfoOfChapterDto();

        dto.id = entity.getId();
        dto.isLiked = entity.isLiked();
        dto.isReaded = entity.isReaded();

        return dto;
    }
}
