package com.company.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;

import com.company.dto.AssetAssignmentDto;
import com.company.dto.AssetAssignmentsMapper;
import com.company.dto.AssetDto;
import com.company.dto.AssetMapper;
import com.company.model.Asset;
import com.company.repository.AssetRepository;

@Service
public class AssetService {

	private AssetRepository assetRepository;
	private AssetMapper assetMapper;
	private Validator validator;

	@Autowired
	public AssetService(AssetRepository assetRepository, AssetMapper assetMapper,Validator validator) {
		this.assetRepository = assetRepository;
		this.assetMapper = assetMapper;
		this.validator= validator;
	}

	public List<AssetDto> getAll() {
		return assetRepository.findAll().stream().map(assetMapper::toDto).collect(Collectors.toList());
	}

	public List<AssetDto> getAllByNameOrSerialNumber(String text) {
		return assetRepository.findAllAssetByNameOrSerialNumber(text).stream().map(assetMapper::toDto)
				.collect(Collectors.toList());
	}

	public List<AssetDto> getAllByName(String name) {
		return assetRepository.findAllAssetBynameContainingIgnoreCase(name).stream().map(assetMapper::toDto)
				.collect(Collectors.toList());
	}

	public Optional<AssetDto> save(AssetDto assetDto) {
		
		validateAssetDto(assetDto);
		
		Optional<AssetDto> assetDtoFinded = assetRepository
				.findOneAssetBySerialNumberIgnoreCase(assetDto.getSerialNumber()).map(assetMapper::toDto);
		assetDtoFinded.ifPresent(u -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Wyposażenie z takim numerem seryjnym już istnieje");
		});

		Asset assetSaved = assetRepository.save(assetMapper.toEntity(assetDto));
		Optional<AssetDto> assetDtoOptional = Optional.of(assetMapper.toDto(assetSaved));
		return assetDtoOptional;
	}

	public Optional<AssetDto> update(AssetDto assetDto) {
		
		validateAssetDto(assetDto);
		
		Optional<AssetDto> assetDtoFinded = assetRepository
				.findOneAssetBySerialNumberIgnoreCase(assetDto.getSerialNumber()).map(assetMapper::toDto);

		if(assetDtoFinded.isPresent()) {
			if (!assetDto.getId().equals(assetDtoFinded.get().getId())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Wyposażenie z takim numerem seryjnym już istnieje");
			}
		}
		
		Asset assetSaved = assetRepository.save(assetMapper.toEntity(assetDto));
		Optional<AssetDto> assetDtoOptional = Optional.of(assetMapper.toDto(assetSaved));

		return assetDtoOptional;
	}

	public Optional<AssetDto> getAssetById(Long id) {
		Optional<Asset> asset = assetRepository.findById(id);

		if (!asset.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wyposażenie o takim id nie istnieje");
		} else {
			return asset.map(assetMapper::toDto);
		}
	}
	
	public List<AssetAssignmentDto> getAssetAssignments(Long assestId){
		Optional<Asset> asset = Optional.ofNullable(assetRepository.getOne(assestId));
		
		if (!asset.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wyposażenie o takim id nie istnieje");
		}
		
		List<AssetAssignmentDto> assetAssignmentsDto = asset.get().getAssignments().stream().map(AssetAssignmentsMapper::toDto).collect(Collectors.toList());
		
		return assetAssignmentsDto;
	}
	
	private void validateAssetDto(AssetDto assetDto) {
		Errors errors = new BeanPropertyBindingResult(assetDto, "assetDto");
		validator.validate(assetDto, errors);
		
		if(errors.hasErrors()) {
			StringBuilder message = new StringBuilder();
			for(ObjectError err : errors.getAllErrors()) {
				message.append(err.getDefaultMessage());
			}
			throw new ResponseStatusException(HttpStatus.CONFLICT, message.toString());
		}
	}
}
