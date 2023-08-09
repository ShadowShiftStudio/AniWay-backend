package com.shadowshiftstudio.aniway.exception.tokens;

public class PasswordResetTokenIsExpiredException extends Exception {
    public PasswordResetTokenIsExpiredException(String message) {
        super(message);
    }
}
