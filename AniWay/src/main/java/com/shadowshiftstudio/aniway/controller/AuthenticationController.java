package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.auth.AuthenticationRequest;
import com.shadowshiftstudio.aniway.dto.auth.AuthenticationResponse;
import com.shadowshiftstudio.aniway.dto.auth.RefreshTokenRequest;
import com.shadowshiftstudio.aniway.dto.auth.RegisterRequest;
import com.shadowshiftstudio.aniway.entity.auth.RefreshToken;
import com.shadowshiftstudio.aniway.exception.tokens.RefreshTokenNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.service.auth.AuthenticationService;
import com.shadowshiftstudio.aniway.service.auth.JwtService;
import com.shadowshiftstudio.aniway.service.auth.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Password reset
    @PostMapping("/forgot_password")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        try {
            return ResponseEntity.ok(service.forgotPassword(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/validate_pass_token")
    public ResponseEntity validatePasswordResetToken(@RequestParam String token) {
        try {
            return ResponseEntity.ok(service.validatePasswordResetToken(token));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset_password")
    public ResponseEntity resetPassword(@RequestParam String password, @RequestParam String token) {
        try {
            return ResponseEntity.ok(service.resetPassword(password, token));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Verify email
    @PostMapping("/send_verification_token")
    public ResponseEntity sendVerificationEmail(@RequestParam String email) {
        try {
            return ResponseEntity.ok(service.sendVerificationEmail(email));
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/verify_email")
    public ResponseEntity validateEmailVerificationToken(@RequestParam String token) {
        try {
            return ResponseEntity.ok(service.validateEmailVerificationToken(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Refresh jwt token
    @PostMapping("/refresh")
    public AuthenticationResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws RefreshTokenNotFoundException {
        String refreshToken = refreshTokenRequest.getToken();
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateToken(user);
                    return AuthenticationResponse
                            .builder()
                            .accessToken(accessToken)
                            .token(refreshToken)
                            .build();
                }).orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));
    }
}
