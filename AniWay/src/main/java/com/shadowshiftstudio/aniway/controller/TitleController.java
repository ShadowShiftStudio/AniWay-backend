package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.title.CreateTitleRequest;
import com.shadowshiftstudio.aniway.dto.title.RateTitleRequest;
import com.shadowshiftstudio.aniway.dto.title.SetTitleReadingStatusRequest;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import com.shadowshiftstudio.aniway.exception.title.TitleNotFoundException;
import com.shadowshiftstudio.aniway.service.chapter.CommentService;
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

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity getTitle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(titleService.getTitle(id));
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteTitle(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(titleService.deleteTitle(id));
        } catch (TitleNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /*@GetMapping("/teams")
    public ResponseEntity getTeams(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(titleService.getTeams(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }*/

    @GetMapping("/chapters")
    public ResponseEntity getChapters(@RequestParam Long id, @RequestParam Long teamId) {
        try {
            return ResponseEntity.ok(titleService.getChapters(id, teamId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/set/rating")
    public ResponseEntity setRating(@RequestBody RateTitleRequest request) {
        try {
            return ResponseEntity.ok(titleService.rateTitle(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/set/reading_status")
    public ResponseEntity setReadingStatus(@RequestBody SetTitleReadingStatusRequest request) {
        try {
            return ResponseEntity.ok(titleService.setTitleReadingStatus(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/comments")
    public ResponseEntity getComments(@RequestParam Long titleId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
            return ResponseEntity.ok(commentService.getTitleComments(titleId, page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/teams")
    public ResponseEntity getTeams(@RequestParam Long titleId) {
        try {
            return ResponseEntity.ok(titleService.getTeams(titleId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
