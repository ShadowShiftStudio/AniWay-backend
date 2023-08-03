package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.UserInfoOfTitlesEntity;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoOfTitleDto {
    private long id;
    private ReadingStatus readingStatus;
    private boolean bookmarked;
    private int rating;

    public static UserInfoOfTitleDto toDto(UserInfoOfTitlesEntity entity) {
        return UserInfoOfTitleDto
                .builder()
                .id(entity.getId())
                .readingStatus(entity.getReadingStatus())
                .bookmarked(entity.isBookmarked())
                .rating(entity.getRating())
                .build();
    }
}
