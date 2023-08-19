package com.shadowshiftstudio.aniway.service.team;

import com.shadowshiftstudio.aniway.dto.team.*;
import com.shadowshiftstudio.aniway.entity.team.TeamEntity;
import com.shadowshiftstudio.aniway.entity.team.UserTeam;
import com.shadowshiftstudio.aniway.entity.team.UserTeamKey;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.enums.Role;
import com.shadowshiftstudio.aniway.exception.team.*;
import com.shadowshiftstudio.aniway.exception.user.UserIsNotAnOwnerOfTeamException;
import com.shadowshiftstudio.aniway.exception.user.UserIsNotTranslatorException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.team.TeamRepository;
import com.shadowshiftstudio.aniway.repository.team.UserTeamRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTeamRepository userTeamRepository;

    @Autowired
    private ImageService imageService;

    public String createTeam(CreateTeamRequest request) throws TeamAlreadyExistsException, UserNotFoundException, UserAlreadyOwnsTeamException {
        if (teamRepository.findByOwner(
                        userRepository.findByUsername(request.getOwnerUsername())
                        .orElseThrow(() -> new UserNotFoundException("User not found"))
                ).isPresent()) {
            throw new UserAlreadyOwnsTeamException("User already owns a team");
        }

        if (teamRepository.findByName(request.getName()).isPresent())
            throw new TeamAlreadyExistsException("Team already exists");

        teamRepository.save(TeamEntity
                .builder()
                .owner(userRepository.findByUsername(request.getOwnerUsername()).orElseThrow(() -> new UserNotFoundException("User not found")))
                .description(request.getDescription())
                .name(request.getName())
                .createdAt(new Date(System.currentTimeMillis()))
                .build()
        );

        return "Team was successfully created";
    }

    public String addUser(AddUserToTeamRequest request) throws UserNotFoundException, UserIsNotTranslatorException, TeamNotFoundException, UserIsNotAnOwnerOfTeamException {
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        TeamEntity team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        UserEntity owner = userRepository.findByUsername(request.getOwnerUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        checkOwnership(owner, team);

        if (user.getRole() != Role.TRANSLATOR)
            throw new UserIsNotTranslatorException("User is not a translator");

        UserTeam userTeam = UserTeam
                .builder()
                .status(request.getStatus())
                .user(user)
                .team(team)
                .id(UserTeamKey
                        .builder()
                        .teamId(team.getId())
                        .userId(user.getId())
                        .build()
                )
                .build();

        teamRepository.save(team.addUser(userTeam));
        userRepository.save(user.addTeam(userTeam));

        return "User added successfully";
    }


    public String editTeam(EditTeamRequest request) throws TeamNotFoundException, UserNotFoundException, UserIsNotAnOwnerOfTeamException {
        TeamEntity team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        UserEntity owner = userRepository.findByUsername(request.getOwnerUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        checkOwnership(owner, team);

        team.setDescription(request.getDescription());
        team.setName(request.getName());

        teamRepository.save(team);

        return "Team edited successfully";
    }

    public String deleteUser(DeleteUserFromTeamRequest request) throws UserNotFoundException, TeamNotFoundException, UserIsNotAnOwnerOfTeamException, TeamOwnerCannotDeleteHimselfException, UserTeamNotFoundException {
        TeamEntity team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        UserEntity owner = userRepository.findByUsername(request.getOwnerUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        checkOwnership(owner, team);

        if (Objects.equals(request.getOwnerUsername(), request.getUsername()))
            throw new TeamOwnerCannotDeleteHimselfException("You can't delete yourself from team");

        UserTeam userTeam = userTeamRepository.findByUserAndTeam(user, team).orElseThrow(() -> new UserTeamNotFoundException("User team not found"));

        team.getUserTeamsInfo().remove(userTeam);
        user.getUserTeamsInfo().remove(userTeam);

        userTeamRepository.delete(userTeam);

        return "User was successfully deleted from the team";
    }

    public String uploadTeamAvatar(UploadTeamAvatarRequest request, MultipartFile avatar) throws TeamNotFoundException, UserNotFoundException, IOException, UserIsNotAnOwnerOfTeamException {
        TeamEntity team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not found"));
        UserEntity owner = userRepository.findByUsername(request.getOwnerUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));

        checkOwnership(owner, team);
        String finalPath = imageService.uploadImage(avatar, "teams/" + team.getName() + "/avatar.jpeg");

        team.setAvatarUrl(finalPath);
        teamRepository.save(team);

        return "Avatar was successfully added";
    }

    private void checkOwnership(UserEntity user, TeamEntity team) throws UserIsNotAnOwnerOfTeamException {
        if (!team.getOwner().equals(user))
            throw new UserIsNotAnOwnerOfTeamException("User is not an owner of team");
    }
}
