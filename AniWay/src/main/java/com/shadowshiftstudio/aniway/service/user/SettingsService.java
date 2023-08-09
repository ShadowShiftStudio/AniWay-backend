package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private UserService userService;
}
