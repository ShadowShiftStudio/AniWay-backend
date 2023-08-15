package com.shadowshiftstudio.aniway.service.title;

import com.shadowshiftstudio.aniway.dto.chapter.GenreDto;
import com.shadowshiftstudio.aniway.entity.chapter.GenreEntity;
import com.shadowshiftstudio.aniway.exception.title.GenreAlreadyExistsException;
import com.shadowshiftstudio.aniway.repository.title.GenreRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRespository genreRespository;

    public List<GenreDto> getGenres() {
        return genreRespository.findAll()
                .stream()
                .map(GenreDto::toDto)
                .toList();
    }

    public String createGenre(String genreName) throws GenreAlreadyExistsException {
        if (genreRespository.findByName(genreName).isPresent())
            throw new GenreAlreadyExistsException("Genre already exists");

        genreRespository.save(GenreEntity
                .builder()
                .name(genreName)
                .build()
        );

        return "Genre was successfully created";
    }
}
