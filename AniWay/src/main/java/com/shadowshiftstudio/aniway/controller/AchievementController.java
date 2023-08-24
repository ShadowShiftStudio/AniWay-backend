package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.achievement.CreateAchievementRequest;
import com.shadowshiftstudio.aniway.service.user.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/achievement")
public class AchievementController {
    @Autowired
    private AchievementService achievementService;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createAchievement(@RequestParam("request") CreateAchievementRequest request,
                                            @RequestParam("avatar") MultipartFile avatar) {
        try {
            return ResponseEntity.ok(achievementService.createAchievement(request, avatar));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
