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
    // TODO URL FOR IMAGE SERVICE
    private String UPLOAD_URL = System.getProperty("user.dir") + "/uploads";

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

    public String uploadImage(Long chapterId, MultipartFile file) throws IOException, ChapterNotFoundException, ChapterImageNotFoundException {
        ChapterEntity chapterEntity = chapterRepository.findById(chapterId).orElseThrow(() -> new ChapterNotFoundException("Chapter not found"));
        String titleName = chapterEntity.getTitle().getName();
        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(
                String.format(
                        "%s/%s/v%d/n%d",
                        UPLOAD_URL,
                        titleName,
                        chapterEntity.getVolume(),
                        chapterEntity.getNumber()
                ),
                file.getOriginalFilename()
        );

        fileNames.append(file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());

        Optional<ChapterImageEntity> chapterImageOptional = chapterImageRepository.findFirstByOrderByImageIndexDesc();
        int imageIndex = chapterImageOptional.map(entity -> entity.getImageIndex() + 1).orElse(1);

        chapterImageRepository.save(
                ChapterImageEntity
                    .builder()
                    .chapter(chapterEntity)
                    .imageIndex(imageIndex)
                    .url(fileNameAndPath.toString())
                    .build()
        );

        return "Uploaded files: " + fileNames;
    }

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

    public List<ChapterImageDto> getTitleImages(Long id) throws ChapterNotFoundException, ChapterImageNotFoundException {
        List<ChapterImageDto> images = chapterImageRepository.findByChapter(id).stream().map(ChapterImageDto::toDto).toList();

        if (images.isEmpty())
            throw new ChapterImageNotFoundException("Images not found");

        return images;
    }
}
