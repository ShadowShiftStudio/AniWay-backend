package com.shadowshiftstudio.aniway.controller;


import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import com.shadowshiftstudio.aniway.service.chapter.CommentService;
import com.shadowshiftstudio.aniway.service.title.TitleService;
import com.shadowshiftstudio.aniway.service.user.AchievementService;
import com.shadowshiftstudio.aniway.service.user.BadgeService;
import com.shadowshiftstudio.aniway.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TitleService titleService;

    @GetMapping("/{username}")
    public ResponseEntity getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserUsername(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/badges")
    public ResponseEntity getUserBadges(@PathVariable String username) {
        try {
            return ResponseEntity.ok(badgeService.getUserBadges(username));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/achievements")
    public ResponseEntity getUserAchievements(@PathVariable String username, @RequestParam boolean received) {
        try {
            return ResponseEntity.ok(achievementService.getUserAchievements(username, received));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/comments")
    public ResponseEntity getUserComments(@PathVariable String username,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize) {
        try {
            return ResponseEntity.ok(commentService.getUserComments(username, page, pageSize));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload/avatar")
    public ResponseEntity uploadAvatarImage(@RequestParam String username,
                                            @RequestParam MultipartFile avatar) {
        try {
            return ResponseEntity.ok(userService.uploadAvatarImage(username, avatar));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{username}/titles")
    public ResponseEntity getUserTitlesByReadingStatus(@PathVariable String username,
                                                       @RequestParam ReadingStatus readingStatus) {
        try {
            return ResponseEntity.ok(titleService.getUserTitlesByReadingStatus(username, readingStatus));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/upload/background")
    public ResponseEntity uploadBackgroundImage(@RequestParam String username,
                                                @RequestParam MultipartFile background) {
        try {
            return ResponseEntity.ok(userService.uploadBackgroundImage(username, background));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
