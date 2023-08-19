package com.shadowshiftstudio.aniway.repository.team;

import com.shadowshiftstudio.aniway.entity.team.TeamEntity;
import com.shadowshiftstudio.aniway.entity.team.UserTeam;
import com.shadowshiftstudio.aniway.entity.team.UserTeamKey;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTeamRepository extends JpaRepository<UserTeam, UserTeamKey> {
    Optional<UserTeam> findByUserAndTeam(UserEntity user, TeamEntity team);
}
