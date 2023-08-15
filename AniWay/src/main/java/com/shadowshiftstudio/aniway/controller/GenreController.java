package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.service.title.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/all")
    public ResponseEntity getGenres() {
        try {
            return ResponseEntity.ok(genreService.getGenres());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity createGenre(@RequestParam String genreName) {
        try {
            return ResponseEntity.ok(genreService.createGenre(genreName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
