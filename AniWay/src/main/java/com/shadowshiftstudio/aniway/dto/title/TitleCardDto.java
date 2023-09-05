package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.UserTitle;
import com.shadowshiftstudio.aniway.enums.TitleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TitleCardDto {
    private Long id;
    private String name;
    private String originalName;
    private TitleType type;
    private String backgroundUrl;
    private double rating;

    public static TitleCardDto toDto(TitleEntity entity) {
        return TitleCardDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .originalName(entity.getOriginalName())
                .type(entity.getType())
                .backgroundUrl(entity.getBackgroundUrl())
                .rating((double) entity
                        .getTitlesInfo()
                        .stream()
                        .map(UserTitle::getRating)
                        .reduce(0, Integer::sum) / entity.getTitlesInfo().size()
                )
                .build();
    }

}
