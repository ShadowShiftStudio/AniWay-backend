package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.title.CreateTitleRequest;
import com.shadowshiftstudio.aniway.dto.title.RateTitleRequest;
import com.shadowshiftstudio.aniway.dto.title.SetTitleReadingStatusRequest;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import com.shadowshiftstudio.aniway.exception.title.TitleNotFoundException;
import com.shadowshiftstudio.aniway.service.title.TitleService;
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
    @PreAuthorize("hasAuthority('TRANSLATOR') or hasAuthority('ADMIN')")
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

    @GetMapping("/get_user_titles/{username}")
    public ResponseEntity getUserTitlesByReadingStatus(@PathVariable String username, @RequestParam ReadingStatus readingStatus) {
        try {
            return ResponseEntity.ok(titleService.getUserTitlesByReadingStatus(username, readingStatus));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/chapters")
    public ResponseEntity getChapters(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(titleService.getChapters(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/rate")
    public ResponseEntity rateTitle(@RequestBody RateTitleRequest request) {
        try {
            return ResponseEntity.ok(titleService.rateTitle(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/set_reading_status")
    public ResponseEntity setTitleReadingStatus(@RequestBody SetTitleReadingStatusRequest request) {
        try {
            return ResponseEntity.ok(titleService.setTitleReadingStatus(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
