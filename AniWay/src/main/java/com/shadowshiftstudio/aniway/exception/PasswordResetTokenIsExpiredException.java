package com.shadowshiftstudio.aniway.exception;

public class PasswordResetTokenIsExpiredException extends Exception {
    public PasswordResetTokenIsExpiredException(String message) {
        super(message);
    }
}
