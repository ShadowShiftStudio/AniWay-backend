package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.UserInfoOfTitlesEntity;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserInfoOfTitleDto {
    private long id;
    private ReadingStatus readingStatus;
    private boolean bookmarked;
    private int rating;

    public static UserInfoOfTitleDto toDto(UserInfoOfTitlesEntity entity) {
        UserInfoOfTitleDto dto = new UserInfoOfTitleDto();

        dto.id = entity.getId();
        dto.readingStatus = entity.getReadingStatus();
        dto.bookmarked = entity.isBookmarked();
        dto.rating = entity.getRating();

        return dto;
    }
}
