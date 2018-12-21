package com.company.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class AssetDto {
	
	@Min(0)
	private Long id;
	@NotBlank(message="Nazwa nie może być pusta")
	private String name;
	private String description;
	@NotBlank(message="Numer Seryjny nie może być pusty")
	private String SerialNumber;
	@NotBlank(message="Kategoria nie może być pusta")
	private String category;

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

	public String getSerialNumber() {
		return SerialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
