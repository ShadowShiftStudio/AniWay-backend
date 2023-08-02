package com.shadowshiftstudio.aniway.controller;


import com.shadowshiftstudio.aniway.dto.UserDto;
import com.shadowshiftstudio.aniway.exception.UserNotFoundException;
import com.shadowshiftstudio.aniway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
