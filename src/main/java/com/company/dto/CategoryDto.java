package com.company.dto;

import java.util.LinkedList;
import java.util.List;

import com.company.model.Asset;

public class CategoryDto {

	private Long id;
	private String name;
	private String description;
	private List<Asset> assets = new LinkedList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

}
