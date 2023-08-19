package com.shadowshiftstudio.aniway.dto.team;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CreateTeamRequest {
    private String ownerUsername;
    private String name;
    private String description;
    private Date createdAt;
}
