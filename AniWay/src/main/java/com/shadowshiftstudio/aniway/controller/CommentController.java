package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.comment.CreateCommentRequest;
import com.shadowshiftstudio.aniway.dto.auth.UpdateCommentRequest;
import com.shadowshiftstudio.aniway.exception.comment.CommentNotFoundException;
import com.shadowshiftstudio.aniway.service.chapter.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/{id}")
    public ResponseEntity getComment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentService.getComment(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity createComment(@RequestBody CreateCommentRequest request) {
        try {
            return ResponseEntity.ok(commentService.createComment(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteComment(
            @RequestParam(name="username") String username,
            @RequestParam(name="comment_id") Long id) {
        try {
            return ResponseEntity.ok(commentService.deleteComment(username, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateComment(@RequestBody UpdateCommentRequest request) {
        try {
            return ResponseEntity.ok(commentService.updateComment(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
