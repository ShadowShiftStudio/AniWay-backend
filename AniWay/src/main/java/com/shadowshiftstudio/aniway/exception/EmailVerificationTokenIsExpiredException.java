package com.shadowshiftstudio.aniway.exception;

public class EmailVerificationTokenIsExpiredException extends Exception {
    public EmailVerificationTokenIsExpiredException(String message) {
        super(message);
    }
}
