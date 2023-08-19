package com.shadowshiftstudio.aniway.entity.team;

import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="team_users")
public class UserTeam {
    @EmbeddedId
    private UserTeamKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("teamId")
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    private String status;

}
