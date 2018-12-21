package com.company.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.dto.CategoryDto;
import com.company.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

	private CategoryService categoryService;

	@Autowired
	public CategoryRestController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/names")
	public ResponseEntity<List<String>> getAllNamesOfCategories(){
		List<String> categoriesDto = categoryService.getAllCategories().stream().map(CategoryDto::getName).collect(Collectors.toList());
		
		return ResponseEntity.ok(categoriesDto);
	}
	
	
}
