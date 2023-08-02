package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.BadgeDto;
import com.shadowshiftstudio.aniway.entity.BadgeEntity;
import com.shadowshiftstudio.aniway.exception.BadgeNotFoundException;
import com.shadowshiftstudio.aniway.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    public BadgeDto getBadge(Long id) throws BadgeNotFoundException {
        Optional<BadgeEntity> badgeOptional = badgeRepository.findById(id);
        BadgeEntity badge;

        if (badgeOptional.isPresent())
            badge = badgeOptional.get();
        else
            throw new BadgeNotFoundException("Badge not found");

        return BadgeDto.toDto(badge);
    }
}
