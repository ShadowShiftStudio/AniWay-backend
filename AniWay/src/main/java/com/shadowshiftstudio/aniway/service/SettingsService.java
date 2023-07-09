package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.SettingsDto;
import com.shadowshiftstudio.aniway.dto.UserDto;
import com.shadowshiftstudio.aniway.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private UserService userService;
    public SettingsDto getSettingsDto(Long id) throws UserNotFoundException {
        try {
            UserDto user = userService.getUser(id);

        }
    }
}
