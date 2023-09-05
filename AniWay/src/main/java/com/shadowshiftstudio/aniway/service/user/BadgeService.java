package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.user.BadgeDto;
import com.shadowshiftstudio.aniway.dto.user.CreateBadgeRequest;
import com.shadowshiftstudio.aniway.entity.user.BadgeEntity;
import com.shadowshiftstudio.aniway.exception.user.BadgeAlreadyExistsException;
import com.shadowshiftstudio.aniway.exception.user.BadgeNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.BadgeRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BadgeService {

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private UserRepository userRepository;

    public BadgeDto getBadge(Long id) throws BadgeNotFoundException {
        return BadgeDto.toDto(badgeRepository.findById(id).orElseThrow(() -> new BadgeNotFoundException("Badge not found")));
    }

    public String createBadge(CreateBadgeRequest request) throws BadgeAlreadyExistsException {
        if (badgeRepository.findByName(request.getName()).isPresent())
            throw new BadgeAlreadyExistsException("Badge with the same name is already exists");

        badgeRepository.save(BadgeEntity
                .builder()
                .name(request.getName())
                .createdAt(new Date(System.currentTimeMillis()))
                .build()
        );

        return "Badge has been successfully created";
    }

    public List<BadgeDto> getUserBadges(String username) throws UserNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getBadges()
                .stream()
                .map(BadgeDto::toDto)
                .toList();
    }
}
