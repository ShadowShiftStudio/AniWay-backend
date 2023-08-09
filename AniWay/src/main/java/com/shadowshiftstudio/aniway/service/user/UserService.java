package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.user.UserDto;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto getUserId(@NotNull Long id) throws UserNotFoundException {
        return UserDto.toDto(userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"))
        );
    }

    public UserDto getUserUsername(@NotNull String username) throws UserNotFoundException {
            return UserDto.toDto(userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"))
            );
    }
}
