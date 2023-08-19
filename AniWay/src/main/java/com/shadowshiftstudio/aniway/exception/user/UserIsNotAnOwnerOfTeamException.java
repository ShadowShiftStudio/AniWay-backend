package com.shadowshiftstudio.aniway.exception.user;

public class UserIsNotAnOwnerOfTeamException extends Exception {
    public UserIsNotAnOwnerOfTeamException(String message) {
        super(message);
    }
}
