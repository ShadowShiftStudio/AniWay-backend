package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.UserDto;
import com.shadowshiftstudio.aniway.entity.UserEntity;
import com.shadowshiftstudio.aniway.exception.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDto getUserId(@NotNull Long id) throws UserNotFoundException {
        try {
            UserEntity user = userRepository.findById(id).get();
            return UserDto.toDto(user);
        } catch(NoSuchElementException e) {
            throw new UserNotFoundException("User not found!");
        }
    }

    public UserDto getUserUsername(@NotNull String username) throws UserNotFoundException {
        try {
            UserEntity user = userRepository.findByUsername(username).get();
            return UserDto.toDto(user);
        } catch(NoSuchElementException e) {
            throw new UserNotFoundException("User not found!");
        }
    }
}
