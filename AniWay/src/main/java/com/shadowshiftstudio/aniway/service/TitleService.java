package com.shadowshiftstudio.aniway.service;

import com.shadowshiftstudio.aniway.dto.CreateTitleRequest;
import com.shadowshiftstudio.aniway.dto.TitleDto;
import com.shadowshiftstudio.aniway.entity.TitleEntity;
import com.shadowshiftstudio.aniway.exception.TitleNotFoundException;
import com.shadowshiftstudio.aniway.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TitleService {
    @Autowired
    private TitleRepository titleRepository;

    public TitleDto getTitle(Long id) throws TitleNotFoundException {
        Optional<TitleEntity> titleOptional = titleRepository.findById(id);
        TitleEntity titleEntity;

        if (titleOptional.isPresent())
            titleEntity = titleOptional.get();
        else
            throw new TitleNotFoundException("Title not found");

        return TitleDto.toDto(titleEntity);
    }

    public String createTitle(CreateTitleRequest request) {
        TitleEntity entity = TitleEntity
                .builder()
                .name(request.getName())
                .originalName(request.getOriginalName())
                .description(request.getDescription())
                .year(request.getYear())
                .type(request.getType())
                .status(request.getStatus())
                .build();

        titleRepository.save(entity);
        return "Title was successfully created";
    }
}
