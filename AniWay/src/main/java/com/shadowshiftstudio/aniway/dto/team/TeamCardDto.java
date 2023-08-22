package com.shadowshiftstudio.aniway.dto.team;


import com.shadowshiftstudio.aniway.entity.team.TeamEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamCardDto {
    private Long id;
    private String name;
    private String avatarUrl;

    public static TeamCardDto toDto(TeamEntity entity) {
        return TeamCardDto
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }
}
