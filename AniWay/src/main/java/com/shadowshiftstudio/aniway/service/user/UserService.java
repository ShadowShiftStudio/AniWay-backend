package com.shadowshiftstudio.aniway.service.user;

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

    public String uploadAvatarImage(Long id, MultipartFile avatar) throws IOException, UserNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        String url = imageService.uploadImage(avatar, "users/" + id + "/avatar.jpeg");

        user.setAvatarUrl(url);
        userRepository.save(user);

        return "Avatar was successfully uploaded on url: " + url;
    }

    public Object uploadBackgroundImage(Long id, MultipartFile background) throws UserNotFoundException, IOException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        String url = imageService.uploadImage(background, "users/" + id + "/background.jpeg");

        user.setBackgroundUrl(url);
        userRepository.save(user);

        return "Avatar was successfully uploaded on url: " + url;
    }
}
