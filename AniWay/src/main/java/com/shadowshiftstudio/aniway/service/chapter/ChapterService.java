package com.shadowshiftstudio.aniway.service.chapter;

import com.shadowshiftstudio.aniway.dto.chapter.ChapterImageDto;
import com.shadowshiftstudio.aniway.dto.chapter.CreateChapterRequest;
import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.chapter.ChapterImageEntity;
import com.shadowshiftstudio.aniway.exception.chapter.ChapterImageNotFoundException;
import com.shadowshiftstudio.aniway.exception.chapter.ChapterNotFoundException;
import com.shadowshiftstudio.aniway.exception.team.TeamNotFoundException;
import com.shadowshiftstudio.aniway.exception.title.TitleNotFoundException;
import com.shadowshiftstudio.aniway.repository.chapter.ChapterImageRepository;
import com.shadowshiftstudio.aniway.repository.chapter.ChapterRepository;
import com.shadowshiftstudio.aniway.repository.team.TeamRepository;
import com.shadowshiftstudio.aniway.repository.title.TitleRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChapterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ChapterImageRepository chapterImageRepository;

    @Autowired
    private ImageService imageService;


    public String createChapter(CreateChapterRequest request) throws TitleNotFoundException, TeamNotFoundException {
        ChapterEntity chapterEntity = ChapterEntity
                .builder()
                .title(titleRepository.findById(request.getTitle_id()).orElseThrow(() -> new TitleNotFoundException("Title not found")))
                .team(teamRepository.findById(request.getTeam_id()).orElseThrow(() -> new TeamNotFoundException("Team not found")))
                .name(request.getName())
                .number(request.getNumber())
                .volume(request.getVolume())
                .createdAt(new Date(System.currentTimeMillis()))
                .updatedAt(new Date(System.currentTimeMillis()))
                .build();

        chapterRepository.save(chapterEntity);
        return "Chapter was successfully created";
    }

    public String uploadChapterImage(MultipartFile file, Long id) throws ChapterNotFoundException, IOException {
        ChapterEntity chapterEntity = chapterRepository.findById(id).orElseThrow(() -> new ChapterNotFoundException("Chapter not found"));
        String titleName = chapterEntity.getTitle().getName();

        Optional<ChapterImageEntity> chapterImageOptional = chapterImageRepository.findFirstByOrderByImageIndexDesc();
        int imageIndex = chapterImageOptional.map(entity -> entity.getImageIndex() + 1).orElse(1);

        String path = String.format("%s/%d%/%d/%d.jpeg", titleName, chapterEntity.getVolume(), chapterEntity.getNumber(), imageIndex);
        String finalPath = imageService.uploadImage(file, path);

        chapterImageRepository.save(
                ChapterImageEntity
                        .builder()
                        .chapter(chapterEntity)
                        .imageIndex(imageIndex)
                        .url(finalPath)
                        .build()
        );

        return "Image was save on path: " + finalPath;

    }
    public List<ChapterImageDto> getChapterImages(Long id) throws ChapterNotFoundException, ChapterImageNotFoundException {
        List<ChapterImageDto> images = chapterImageRepository
                .findByChapter(chapterRepository.findById(id).orElseThrow(() -> new ChapterNotFoundException("Chapter not found")))
                .stream()
                .map(ChapterImageDto::toDto)
                .toList();

        if (images.isEmpty())
            throw new ChapterImageNotFoundException("Images not found");

        return images;
    }
}
