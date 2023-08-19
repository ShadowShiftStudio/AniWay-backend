package com.shadowshiftstudio.aniway.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserFromTeamRequest {
    private Long teamId;
    private String ownerUsername;
    private String username;
}
