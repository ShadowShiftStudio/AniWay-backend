package com.shadowshiftstudio.aniway.service.title;

import com.shadowshiftstudio.aniway.dto.title.CreateTitleRequest;
import com.shadowshiftstudio.aniway.dto.title.TitleDto;
import com.shadowshiftstudio.aniway.entity.CategoryEntity;
import com.shadowshiftstudio.aniway.entity.chapter.GenreEntity;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.UserTitle;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import com.shadowshiftstudio.aniway.exception.title.CategoryNotFoundException;
import com.shadowshiftstudio.aniway.exception.title.GenreNotFoundException;
import com.shadowshiftstudio.aniway.exception.title.TitleNotFoundException;
import com.shadowshiftstudio.aniway.exception.title.TitlesNotFoundException;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.title.CategoryRepository;
import com.shadowshiftstudio.aniway.repository.title.GenreRespository;
import com.shadowshiftstudio.aniway.repository.title.TitleRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.repository.user.UserTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TitleService {
    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private UserTitleRepository userTitleRepository;

    @Autowired
    private GenreRespository genreRespository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public TitleDto getTitle(Long id) throws TitleNotFoundException {
        Optional<TitleEntity> titleOptional = titleRepository.findById(id);
        TitleEntity titleEntity;

        if (titleOptional.isPresent())
            titleEntity = titleOptional.get();
        else
            throw new TitleNotFoundException("Title not found");

        titleEntity.setViews(titleEntity.getViews() + 1);
        titleRepository.save(titleEntity);

        return TitleDto.toDto(titleEntity);
    }

    public String createTitle(CreateTitleRequest request) throws GenreNotFoundException, CategoryNotFoundException {
        TitleEntity entity = TitleEntity
                .builder()
                .name(request.getName())
                .originalName(request.getOriginalName())
                .description(request.getDescription())
                .year(request.getYear())
                .type(request.getType())
                .ageRating(request.getAgeRating())
                .status(request.getStatus())
                .build();

        // MAYBE UNNECESSARY OPERATION
        TitleEntity finalEntity = titleRepository.save(entity);

        addGenres(finalEntity, request.getGenres_ids());
        addCategories(finalEntity, request.getCategory_ids());

        titleRepository.save(finalEntity);

        return "Title was successfully created";
    }

    public String deleteTitle(Long id) throws TitleNotFoundException {
        Optional<TitleEntity> titleOptional = titleRepository.findById(id);
        TitleEntity titleEntity;

        if (titleOptional.isPresent())
            titleEntity = titleOptional.get();
        else
            throw new TitleNotFoundException("Title not found");

        titleRepository.delete(titleEntity);

        return "Title was successfully deleted";
    }

    public List<TitleDto> getUserTitlesByReadingStatus(String username, ReadingStatus readingStatus) throws UserNotFoundException, TitlesNotFoundException {
        List<TitleDto> titles = userTitleRepository.findAllByUserAndReadingStatus(
                userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found")),
                readingStatus
        )
                .stream()
                .map(UserTitle::getTitle)
                .map(TitleDto::toDto)
                .toList();

        if (titles.isEmpty())
            throw new TitlesNotFoundException("Titles not found");

        return titles;
    }

    private void addGenres(TitleEntity entity, List<Long> genresIds) throws GenreNotFoundException {
        for(Long id : genresIds) {
            entity.addGenre(genreRespository
                    .findById(id)
                    .orElseThrow(() -> new GenreNotFoundException("Genre not found"))
            );
        }
    }

    private void addCategories(TitleEntity entity, List<Long> categoryIds) throws CategoryNotFoundException {
        for (Long id : categoryIds) {
            entity.addCategory(categoryRepository
                    .findById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found"))
            );
        }
    }
}
