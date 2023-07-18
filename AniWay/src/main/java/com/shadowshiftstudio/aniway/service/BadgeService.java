package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.BadgeDto;
import com.shadowshiftstudio.aniway.exception.BadgeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BadgeService {

    @Autowired
    private BadgeService badgeService;

    public BadgeDto getBadges(Long id) throws BadgeNotFoundException {
        try {
            BadgeDto badge = badgeService.getBadges(id);
            return badge;
        } catch (NoSuchElementException e) {
            throw new BadgeNotFoundException("User not found!");
        }
    }
}
