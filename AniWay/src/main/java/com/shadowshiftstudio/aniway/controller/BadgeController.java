package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.user.CreateBadgeRequest;
import com.shadowshiftstudio.aniway.service.user.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/badge")
public class BadgeController {
    @Autowired
    private BadgeService badgeService;

    @GetMapping("/{id}")
    public ResponseEntity getBadge(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(badgeService.getBadge(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createBadge(@RequestBody CreateBadgeRequest request) {
        try {
            return ResponseEntity.ok(badgeService.createBadge(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
