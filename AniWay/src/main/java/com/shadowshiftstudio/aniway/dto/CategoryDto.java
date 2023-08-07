package com.shadowshiftstudio.aniway.dto;

import com.shadowshiftstudio.aniway.entity.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String text;

    public static CategoryDto toDto(CategoryEntity entity) {
        // TODO
        return null;
//        return CategoryDto
//                .builder()
//                .id(entity.getId())
//                .text(entity.getText())
//                .build();
    }
}
