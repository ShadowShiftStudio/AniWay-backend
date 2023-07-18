package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.exception.UserNotFoundException;
import com.shadowshiftstudio.aniway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manga")
public class MangaController {
    @Autowired
    private MangaService mangaService;

    // TODO add query params [preview, page] 122 string in open-api.yml
    @GetMapping("/{id}")
    public ResponseEntity getManga(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mangaService.getManga(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity createManga(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mangaService.createManga(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
