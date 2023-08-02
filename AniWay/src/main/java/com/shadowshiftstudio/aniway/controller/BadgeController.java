package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.exception.BadgeNotFoundException;
import com.shadowshiftstudio.aniway.service.BadgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/badge")
public class BadgeController {
    @Autowired
    private BadgeService badgeService;

    @GetMapping("/{id}")
    public ResponseEntity getBadge(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(badgeService.getBadge(id));
        } catch (BadgeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
