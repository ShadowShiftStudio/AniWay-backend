package com.shadowshiftstudio.aniway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievment")
public class AchievmentController {
/*    @Autowired
    private AchievmentService achievmentService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createAchievment(@RequestBody CreateAchievmentRequest request) {
        try {
            return ResponseEntity.ok(achievmentService.createAchievment(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

*/
}
