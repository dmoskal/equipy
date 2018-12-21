package com.company.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.dto.CategoryDto;
import com.company.dto.CategoryMapper;
import com.company.repository.CategoryRepository;

@Service
public class CategoryService {

	
	private CategoryRepository categoryRepository;
	
	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	
	public List<CategoryDto> getAllCategories(){
		return categoryRepository.findAll().stream().map(CategoryMapper::toDto).collect(Collectors.toList());
	}
}
