package com.shadowshiftstudio.aniway.dto.team;

import com.shadowshiftstudio.aniway.dto.title.TitleCardDto;
import com.shadowshiftstudio.aniway.dto.user.UserCardDto;
import com.shadowshiftstudio.aniway.entity.team.TeamEntity;
import com.shadowshiftstudio.aniway.entity.team.UserTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {
    private Long id;
    private List<UserCardDto> users;
    private String name;
    private String description;
    private Date createdAt;
    private String avatarUrl;
    private List<TitleCardDto> titles;

    public static TeamDto toDto(TeamEntity entity) {
        return TeamDto
                .builder()
                .id(entity.getId())
                .users(entity
                        .getUserTeamsInfo()
                        .stream()
                        .map(UserTeam::getUser)
                        .map(UserCardDto::toDto)
                        .limit(12)
                        .toList()
                )
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .avatarUrl(entity.getAvatarUrl())
                /*.titles(entity
                        .getTitles()
                        .stream()
                        .map(TitleCardDto::toDto)
                        .limit(12)
                        .toList()
                )*/
                .build();
    }
}
