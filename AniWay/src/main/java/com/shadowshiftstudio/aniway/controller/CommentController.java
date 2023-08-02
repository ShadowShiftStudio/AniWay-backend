package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.CreateCommentRequest;
import com.shadowshiftstudio.aniway.dto.UpdateCommentRequest;
import com.shadowshiftstudio.aniway.exception.CommentNotFoundException;
import com.shadowshiftstudio.aniway.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
        } catch (CommentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(commentService.deleteComment(id));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // TODO think about correct mapping path
    @PutMapping("/")
    public ResponseEntity updateComment(@RequestBody UpdateCommentRequest request) {
        try {
            return ResponseEntity.ok(commentService.updateComment(request));
        } catch (CommentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
