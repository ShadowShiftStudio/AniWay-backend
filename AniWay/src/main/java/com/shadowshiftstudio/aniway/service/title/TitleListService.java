package com.shadowshiftstudio.aniway.service.title;

import com.shadowshiftstudio.aniway.dto.title.TitleDto;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import com.shadowshiftstudio.aniway.exception.title.TitlesNotFoundException;
import com.shadowshiftstudio.aniway.repository.title.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleListService {
    @Autowired
    private TitleRepository titleRepository;

    public List<TitleDto> getCatalogTitles(
            List<String> genres,
            List<TitleStatus> statuses,
            List<TitleType> types,
            List<String> categories,
            List<AgeRating> ratings
    ) throws TitlesNotFoundException {
        List<TitleDto> titles = titleRepository.findSpecificTitles(genres, statuses, types, categories, ratings)
                .stream()
                .map(TitleDto::toDto)
                .toList();

        if (titles.isEmpty())
            throw new TitlesNotFoundException("Titles not found");

        return titles;
    }

}
