package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.team.*;
import com.shadowshiftstudio.aniway.service.team.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/{team_name}")
    public ResponseEntity getTeam(@PathVariable(name = "team_name") String teamName) {
        try {
            return ResponseEntity.ok(teamService.getTeam(teamName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TRANSLATOR')")
    public ResponseEntity createTeam(@RequestBody CreateTeamRequest request) {
        try {
            return ResponseEntity.ok(teamService.createTeam(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TRANSLATOR')")
    public ResponseEntity addUser(@RequestBody AddUserToTeamRequest request) {
        try {
            return ResponseEntity.ok(teamService.addUser(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('TRANSLATOR')")
    public ResponseEntity editTeam(@RequestBody EditTeamRequest request) {
        try {
            return ResponseEntity.ok(teamService.editTeam(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete_user")
    @PreAuthorize("hasAuthority('TRANSLATOR')")
    public ResponseEntity deleteUser(@RequestBody DeleteUserFromTeamRequest request) {
        try {
            return ResponseEntity.ok(teamService.deleteUser(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/avatar/upload")
    @PreAuthorize("hasAuthority('TRANSLATOR')")
    public ResponseEntity uploadAvatar(
            @RequestPart("data") UploadTeamAvatarRequest request,
            @RequestPart("avatar") MultipartFile avatar
    ) {
        try {
            return ResponseEntity.ok(teamService.uploadTeamAvatar(request, avatar));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
