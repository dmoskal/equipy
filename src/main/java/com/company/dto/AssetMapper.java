package com.company.dto;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.model.Asset;
import com.company.model.Category;
import com.company.repository.CategoryRepository;

@Component
public class AssetMapper {

	@Autowired
	private CategoryRepository categoryRepository;
	
	public AssetMapper() {
	}
	
	public Asset toEntity(AssetDto assetDto) {
		Asset asset = new Asset();
		Optional<Category> category = categoryRepository.findOneCategoryByName(assetDto.getCategory());
		asset.setId(assetDto.getId());
		asset.setName(assetDto.getName());
		asset.setSerialNumber(assetDto.getSerialNumber());
		asset.setDescription(assetDto.getDescription());
		asset.setCategory(category.get());
		return asset;
	}

	public AssetDto toDto(Asset asset) {
		AssetDto assetDto = new AssetDto();
		assetDto.setId(asset.getId());
		assetDto.setName(asset.getName());
		assetDto.setSerialNumber(asset.getSerialNumber());
		assetDto.setDescription(asset.getDescription());
		assetDto.setCategory(asset.getCategory().getName());
		return assetDto;
	}
}
