package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTitleRequest {
    private String name;
    private String originalName;
    private int year;
    private String description;
    private TitleStatus status;
    private TitleType type;
    private AgeRating ageRating;
}
