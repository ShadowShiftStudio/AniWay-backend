package com.shadowshiftstudio.aniway.service.user;

import com.shadowshiftstudio.aniway.dto.user.BadgeDto;
import com.shadowshiftstudio.aniway.dto.user.UserDto;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.service.image.ImageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

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

    public String uploadAvatarImage(String username, MultipartFile avatar) throws IOException, UserNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        String url = imageService.uploadImage(avatar, "users/" + username + "/avatar.jpeg");

        user.setAvatarUrl(url);
        userRepository.save(user);

        return "Avatar was successfully uploaded on url: " + url;
    }

    public Object uploadBackgroundImage(String username, MultipartFile background) throws UserNotFoundException, IOException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        String url = imageService.uploadImage(background, "users/" + username + "/background.jpeg");

        user.setBackgroundUrl(url);
        userRepository.save(user);

        return "Avatar was successfully uploaded on url: " + url;
    }


}
