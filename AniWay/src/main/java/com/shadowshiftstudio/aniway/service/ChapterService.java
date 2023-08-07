package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.CreateChapterRequest;
import com.shadowshiftstudio.aniway.entity.ChapterEntity;
import com.shadowshiftstudio.aniway.exception.TitleNotFoundException;
import com.shadowshiftstudio.aniway.repository.TitleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class ChapterService {
    // TODO URL FOR IMAGE SERVICE
    private String UPLOAD_URL = System.getProperty("user.dir") + "/uploads";

    private TitleRepository titleRepository;
//    private TeamRepository teamRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(UPLOAD_URL, file.getOriginalFilename());

        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        return "Uploaded files: " + fileNames;
    }

    public String createChapter(CreateChapterRequest request) throws TitleNotFoundException {

        // TODO
        return null;
//        ChapterEntity chapterEntity = ChapterEntity
//                .builder()
//                .title(titleRepository.findById(request.getTitle_id()).orElseThrow(() -> new TitleNotFoundException("Title not found")))
//                .team(teamRepository.findById(request.getTeam_id()).orElseThrow(() -> new TeamNotFoundException("Team not found")))
//                .name(request.getName())
//                .number(request.getNumber())
//                .volume(request.getVolume())
//                .createdAt(new Date(System.currentTimeMillis()))
//                .updatedAt(new Date(System.currentTimeMillis()))
//                .build();
    }
}
