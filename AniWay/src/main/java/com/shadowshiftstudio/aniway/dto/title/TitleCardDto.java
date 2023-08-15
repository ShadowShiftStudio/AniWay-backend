package com.shadowshiftstudio.aniway.dto.title;

import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
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

    private TitleType type;

    private String backgroundUrl;

    public static TitleCardDto toDto(TitleEntity entity) {
        return TitleCardDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .backgroundUrl(entity.getBackgroundUrl())
                .build();
    }

}
