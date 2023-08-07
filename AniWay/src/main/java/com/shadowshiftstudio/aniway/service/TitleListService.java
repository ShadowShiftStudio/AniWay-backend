package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.CategoryDto;
import com.shadowshiftstudio.aniway.dto.GenreDto;
import com.shadowshiftstudio.aniway.dto.TitleDto;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.SortBy;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import com.shadowshiftstudio.aniway.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleListService {
    @Autowired
    private TitleRepository titleRepository;

    public List<TitleDto> getCatalogTitles(
            List<GenreDto> genres,
            List<TitleStatus> status,
            List<TitleType> type,
            List<CategoryDto> categories,
            List<AgeRating> rating,
            SortBy sortBy
    ) {
        titleRepository.findSpecificTitles(genres, status, type, categories, rating);
    }

}
