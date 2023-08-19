package com.shadowshiftstudio.aniway.controller;

import com.shadowshiftstudio.aniway.enums.AgeRating;
import com.shadowshiftstudio.aniway.enums.TitleStatus;
import com.shadowshiftstudio.aniway.enums.TitleType;
import com.shadowshiftstudio.aniway.service.title.TitleListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/titles")
public class TitleListController {
    @Autowired
    private TitleListService titleListService;

    @GetMapping("/catalog")
    public ResponseEntity getCatalogTitles(
            @RequestParam(required = false) List<String> genres,
            @RequestParam(required = false) List<TitleStatus> statuses,
            @RequestParam(required = false) List<TitleType> types,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<AgeRating> ageRatings,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "18") int pageSize
            ) {
        try {
            return ResponseEntity.ok(titleListService.getCatalogTitles(
                        genres,
                        statuses,
                        types,
                        categories,
                        ageRatings,
                        page,
                        pageSize
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

/*    @GetMapping("/related")
    public ResponseEntity getRelatedTitles(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(titleListService.getRelatedTitles(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
*/
    @GetMapping("/top")
    public ResponseEntity getTopTitles() {
        // TODO
        return null;
//        try {
//            return ResponseEntity.ok(titleListService.getTopTitles());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
    }
}
