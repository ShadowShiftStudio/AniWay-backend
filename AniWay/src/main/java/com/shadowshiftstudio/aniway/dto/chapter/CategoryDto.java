package com.shadowshiftstudio.aniway.dto.chapter;

//import com.shadowshiftstudio.aniway.entity.CategoryEntity;
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
        return CategoryDto
                .builder()
                .id(entity.getId())
                .text(entity.getText())
                .build();
    }
}
