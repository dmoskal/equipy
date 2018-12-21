package com.company.dto;

import com.company.model.Category;

public class CategoryMapper {

	public static Category toEntity(CategoryDto categoryDto) {
		Category category = new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		category.setDescription(categoryDto.getDescription());
		category.setAssets(categoryDto.getAssets());
		return category;
	}

	public static CategoryDto toDto(Category category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setName(category.getName());
		categoryDto.setDescription(category.getDescription());
		categoryDto.setAssets(category.getAssets());
		return categoryDto;
	}
}
