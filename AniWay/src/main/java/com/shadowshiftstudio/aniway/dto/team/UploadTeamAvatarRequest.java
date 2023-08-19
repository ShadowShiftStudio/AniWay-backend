package com.shadowshiftstudio.aniway.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadTeamAvatarRequest {
    private Long teamId;
    private String ownerUsername;
}
