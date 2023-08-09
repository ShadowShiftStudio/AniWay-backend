package com.shadowshiftstudio.aniway.exception.tokens;

public class EmailVerificationTokenIsExpiredException extends Exception {
    public EmailVerificationTokenIsExpiredException(String message) {
        super(message);
    }
}
