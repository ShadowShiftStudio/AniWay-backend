package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.user.BadgeDto;
import com.shadowshiftstudio.aniway.entity.user.BadgeEntity;
import com.shadowshiftstudio.aniway.exception.user.BadgeNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
