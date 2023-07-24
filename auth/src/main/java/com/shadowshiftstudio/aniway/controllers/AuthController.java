package com.shadowshiftstudio.aniway.controllers;

import com.shadowshiftstudio.aniway.dto.LoginDto;
import com.shadowshiftstudio.aniway.dto.RefreshTokenDto;
import com.shadowshiftstudio.aniway.dto.TokensPairDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @PostMapping("/login")
  ResponseEntity<TokensPairDto> login(@RequestBody LoginDto requestBody) {
    // TOOD
    return null;
  }

  @PostMapping("/refresh-token")
  ResponseEntity<TokensPairDto> refreshToken(@RequestBody RefreshTokenDto requestBody) {
    // TOOD
    return null;
  }
}
