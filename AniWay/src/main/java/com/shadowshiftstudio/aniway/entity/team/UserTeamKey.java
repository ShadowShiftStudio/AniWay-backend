package com.shadowshiftstudio.aniway.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTeamKey implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "team_id")
    private Long teamId;
}
