package com.shadowshiftstudio.aniway.controller;


import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/id/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserId(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserUsername(username));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload_avatar")
    public ResponseEntity uploadAvatarImage(@RequestParam Long id,
                                            @RequestParam MultipartFile avatar) {
        try {
            return ResponseEntity.ok(userService.uploadAvatarImage(id, avatar));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload_background")
    public ResponseEntity uploadBackgroundImage(@RequestParam Long id,
                                                @RequestParam MultipartFile background) {
        try {
            return ResponseEntity.ok(userService.uploadBackgroundImage(id, background));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/badges")
    public ResponseEntity getUserBadges(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.getUserBadges(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
