package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.CreateTitleRequest;
import com.shadowshiftstudio.aniway.exception.TitleNotFoundException;
import com.shadowshiftstudio.aniway.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/title")
public class TitleController {
    @Autowired
    private TitleService titleService;

    @GetMapping("/{id}")
    public ResponseEntity getTitle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(titleService.getTitle(id));
        } catch (TitleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('TRANSLATOR')")
    public ResponseEntity createTitle(@RequestBody CreateTitleRequest request) {
        try {
            return ResponseEntity.ok(titleService.createTitle(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ResponseEntity deleteTitle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(titleService.deleteTitle(id));
        } catch (TitleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
