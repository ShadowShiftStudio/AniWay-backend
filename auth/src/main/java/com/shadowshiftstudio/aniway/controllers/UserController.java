package com.shadowshiftstudio.aniway.controllers;

import com.shadowshiftstudio.aniway.dto.CreateUserDto;
import com.shadowshiftstudio.aniway.dto.TokensPairDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {
  @PostMapping
  ResponseEntity<?> createUser(@RequestBody CreateUserDto requestBody) {
    // TODO
    return null;
  }
}
