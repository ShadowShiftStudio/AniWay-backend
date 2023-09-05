package com.shadowshiftstudio.aniway.service.title;

import com.shadowshiftstudio.aniway.dto.chapter.ChapterDto;
import com.shadowshiftstudio.aniway.dto.team.TeamCardDto;
import com.shadowshiftstudio.aniway.dto.title.*;
import com.shadowshiftstudio.aniway.entity.chapter.ChapterEntity;
import com.shadowshiftstudio.aniway.entity.title.TitleEntity;
import com.shadowshiftstudio.aniway.entity.user.UserEntity;
import com.shadowshiftstudio.aniway.entity.user.UserTitle;
import com.shadowshiftstudio.aniway.entity.user.keys.UserTitleKey;
import com.shadowshiftstudio.aniway.enums.ReadingStatus;
import com.shadowshiftstudio.aniway.exception.chapter.ChapterNotFoundException;
import com.shadowshiftstudio.aniway.exception.team.TeamNotFoundException;
import com.shadowshiftstudio.aniway.exception.title.*;
import com.shadowshiftstudio.aniway.exception.user.UserNotFoundException;
import com.shadowshiftstudio.aniway.repository.chapter.ChapterRepository;
import com.shadowshiftstudio.aniway.repository.team.TeamRepository;
import com.shadowshiftstudio.aniway.repository.title.CategoryRepository;
import com.shadowshiftstudio.aniway.repository.title.GenreRespository;
import com.shadowshiftstudio.aniway.repository.title.TitleRepository;
import com.shadowshiftstudio.aniway.repository.user.UserRepository;
import com.shadowshiftstudio.aniway.repository.user.UserTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TeamRepository teamRepository;

    public TitleDto getTitle(Long id, String username) throws TitleNotFoundException, UserNotFoundException {
        TitleEntity title = titleRepository.findById(id).orElseThrow(() -> new TitleNotFoundException("Title not found"));
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<UserTitle> userTitleOptional = userTitleRepository.findByUserAndTitle(user, title);
        if (userTitleOptional.isEmpty()) {
            UserTitle userTitle = UserTitle
                    .builder()
                    .user(user)
                    .title(title)
                    .id(UserTitleKey
                            .builder()
                            .titleId(title.getId())
                            .userId(user.getId())
                            .build())
                    .build();
            titleRepository.save(title.addTitleInfo(userTitle));
            return TitleDto.toDto(userTitle);
        }

        return TitleDto.toDto(userTitleOptional.get());
    }

    public Set<TeamCardDto> getTeams(Long titleId) throws TitleNotFoundException {
        TitleEntity title = titleRepository
                .findById(titleId)
                .orElseThrow(() -> new TitleNotFoundException("Title not found"));

        return title
                .getChapters()
                .stream()
                .map(ChapterEntity::getTeam)
                .map(TeamCardDto::toDto)
                .collect(Collectors.toSet());
    }

    public String createTitle(CreateTitleRequest request) throws GenreNotFoundException, CategoryNotFoundException {
        List<Long> genresIds = request.getGenres_ids();
        List<Long> categoryIds = request.getCategory_ids();

        TitleEntity entity = TitleEntity
                .builder()
                .name(request.getName())
                .originalName(request.getOriginalName())
                .description(request.getDescription())
                .year(request.getYear())
                .type(request.getType())
                .ageRating(request.getAgeRating())
                .status(request.getStatus())
                .categories(new HashSet<>())
                .genres(new HashSet<>())
                .build();

        TitleEntity finalEntity = titleRepository.save(entity);

        if (genresIds != null) {
            addGenres(finalEntity, genresIds);
        }

        if (categoryIds != null) {
            addCategories(finalEntity, categoryIds);
        }


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

    public List<TitleDto> getUserTitlesByReadingStatus(String username, ReadingStatus readingStatus) throws
            UserNotFoundException, TitlesNotFoundException {
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
        for (Long id : genresIds) {
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

    public String rateTitle(RateTitleRequest request) throws UserNotFoundException, TitleNotFoundException {
        UserTitle userTitle = getUserTitleByUsernameAndTitle(request.getUsername(), request.getTitleId());

        userTitle.setRating(request.getRating());
        userTitleRepository.save(userTitle);

        return "Title was successfully rated";
    }

    public String setTitleReadingStatus(SetTitleReadingStatusRequest request) throws
            UserNotFoundException, TitleNotFoundException {
        UserTitle userTitle = getUserTitleByUsernameAndTitle(request.getUsername(), request.getTitleId());

        userTitle.setReadingStatus(request.getStatus());
        userTitleRepository.save(userTitle);

        return "Title was successfully added to reading category";
    }

    private UserTitle getUserTitleByUsernameAndTitle(String username, Long titleId) throws
            UserNotFoundException, TitleNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
        TitleEntity title = titleRepository.findById(titleId).orElseThrow(() -> new TitleNotFoundException("Title not found"));

        return userTitleRepository.findByUserAndTitle(user, title)
                .orElse(UserTitle
                        .builder()
                        .user(user)
                        .title(title)
                        .id(UserTitleKey
                                .builder()
                                .titleId(title.getId())
                                .userId(user.getId())
                                .build()
                        )
                        .build());
    }

    public List<ChapterDto> getChapters(Long id, Long teamId) throws
            TitleNotFoundException, ChapterNotFoundException, TeamNotFoundException {
        List<ChapterDto> chapters = chapterRepository
                .findAllByTitleAndTeam(
                        titleRepository.findById(id).orElseThrow(() -> new TitleNotFoundException("Title not found")),
                        teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException("Team not found"))
                ).stream()
                .map(ChapterDto::toDto)
                .toList();


        if (chapters.isEmpty())
            throw new ChapterNotFoundException("Chapters not found");

        return chapters;
    }

    public List<TitleCardDto> searchTitles(String name) throws NoTitlesFoundException {
        List<TitleEntity> titles = titleRepository
                .findAllByNameIsLikeOrOriginalNameIsLike(name, name);

        if (titles.isEmpty())
            throw new NoTitlesFoundException("No titles found");

        return titles.stream()
                .map(TitleCardDto::toDto)
                .toList();
    }

    /*public List<TeamCardDto> getTeams(Long id) throws TitleNotFoundException {
        return titleRepository
                .findById(id)
                .orElseThrow(() -> new TitleNotFoundException("Title not found"))
                .getTeams()
                .stream()
                .map(TeamCardDto::toDto)
                .toList();
    }*/
}
