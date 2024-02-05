package com.example.localguidebe.converter;

import com.example.localguidebe.dto.CategoryDTO;
import com.example.localguidebe.entity.Category;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryToCategoryDtoConverter {
    public static Set<CategoryDTO> convertSetCategory(Set<Category> categories){
        Set<CategoryDTO> categoryDTOS = new HashSet<>();
        for(Category category : categories){
            categoryDTOS.add(new CategoryDTO(category.getId(), category.getName()));
        }
        return categoryDTOS;
    }
}
