package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.CreateChapterRequest;
import com.shadowshiftstudio.aniway.service.ChapterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    private ChapterService chapterService;

    @PostMapping("/create")
    public ResponseEntity createChapter(@RequestBody CreateChapterRequest request) {
        try {
            return ResponseEntity.ok(chapterService.createChapter(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getImage(@PathVariable Long id, @RequestParam int page) {
        // TODO
        return null;
//        try {
//            return ResponseEntity.ok(chapterService.getImage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }
}
