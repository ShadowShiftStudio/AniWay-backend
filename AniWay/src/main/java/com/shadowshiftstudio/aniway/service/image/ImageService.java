package com.shadowshiftstudio.aniway.service.image;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {
    private String UPLOAD_URL = System.getProperty("user.dir") + "/uploads";
    public String uploadImage(MultipartFile file, String path) throws IOException {
        Path finalPath = Paths.get(
                String.format(
                        "%s/%s",
                        UPLOAD_URL,
                        path));

        Files.write(finalPath, file.getBytes());

        return finalPath.toString();
    }
}
