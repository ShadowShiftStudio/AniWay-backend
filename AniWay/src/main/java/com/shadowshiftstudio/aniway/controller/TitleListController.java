package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.dto.CategoryDto;
import com.shadowshiftstudio.aniway.dto.GenreDto;
import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.SortBy;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import com.shadowshiftstudio.aniway.service.TitleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleListController {
    @Autowired
    private TitleListService titleListService;

    @GetMapping("/catalog")
    public ResponseEntity getCatalogTitles(@RequestParam(required = false) List<GenreDto> genres,
                                           @RequestParam(required = false) List<TitleStatus> status,
                                           @RequestParam(required = false) List<TitleType> type,
                                           @RequestParam(required = false) List<CategoryDto> categories,
                                           @RequestParam(required = false) List<AgeRating> rating,
                                           @RequestParam(required = false) SortBy sortBy) {
        try {
            return ResponseEntity.ok(titleListService.getCatalogTitles(
                        genres,
                        status,
                        type,
                        categories,
                        rating,
                        sortBy
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/top")
    public ResponseEntity getTopTitles() {
        try {
            return ResponseEntity.ok(titleListService.getTopTitles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
