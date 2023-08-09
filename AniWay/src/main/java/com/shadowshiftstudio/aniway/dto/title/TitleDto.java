package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TitleDto {
    private long id;
    private String name;
    private String originalName;
    private int year;
    private String description;
    private TitleStatus status;
    private TitleType type;
    private int views;

    private AgeRating ageRating;
    public static TitleDto toDto(TitleEntity entity) {
        return TitleDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .originalName(entity.getOriginalName())
                .year(entity.getYear())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .type(entity.getType())
                .views(entity.getViews())
                .ageRating(entity.getAgeRating())
                .build();
    }
}
