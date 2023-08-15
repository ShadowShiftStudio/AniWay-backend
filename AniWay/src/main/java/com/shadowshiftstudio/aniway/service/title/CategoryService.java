package com.shadowshiftstudio.aniway.service.title;

import com.shadowshiftstudio.aniway.dto.chapter.CategoryDto;
import com.shadowshiftstudio.aniway.entity.CategoryEntity;
import com.shadowshiftstudio.aniway.exception.title.CategoryAlreadyExistsException;
import com.shadowshiftstudio.aniway.repository.title.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::toDto)
                .toList();
    }

    public String createCategory(String categoryText) throws CategoryAlreadyExistsException {
        if (categoryRepository.findByText(categoryText).isPresent())
            throw new CategoryAlreadyExistsException("Category already exists");

        categoryRepository.save(CategoryEntity
                .builder()
                .text(categoryText)
                .build()
        );

        return "Category created";
    }
}
