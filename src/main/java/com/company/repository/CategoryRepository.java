package com.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	public Optional<Category> findOneCategoryByName(String name);
}
