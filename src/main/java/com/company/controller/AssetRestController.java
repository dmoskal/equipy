package com.company.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.company.dto.AssetAssignmentDto;
import com.company.dto.AssetDto;
import com.company.service.AssetService;

@RestController
@RequestMapping("/api/assets")
public class AssetRestController {

	private AssetService assetService;

	@Autowired
	public AssetRestController(AssetService assetService) {
		this.assetService = assetService;
	}

	@GetMapping("")
	public List<AssetDto> getAllAssets(@RequestParam(required = false) String text) {
		if (text != null) {
			return assetService.getAllByNameOrSerialNumber(text);
		} else {
			return assetService.getAll();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<AssetDto> getUser(@PathVariable Long id) {
		Optional<AssetDto> assetDto = assetService.getAssetById(id);
		return ResponseEntity.ok(assetDto.get());
	}

	@PutMapping("/{id}")
	public ResponseEntity<AssetDto> updateUser(@PathVariable Long id, @RequestBody AssetDto assetDto) {
		if (!id.equals(assetDto.getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
		}

		Optional<AssetDto> assetDtoSaved = assetService.update(assetDto);

		return ResponseEntity.ok(assetDtoSaved.get());
	}

	@PostMapping("")
	public ResponseEntity<AssetDto> saveAsset(@RequestBody AssetDto assetDto) {
		if (assetDto.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wyposażenie posiada już id");
		}

		Optional<AssetDto> assetDtoSaved = assetService.save(assetDto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
				.buildAndExpand(assetDtoSaved.get().getId()).toUri();
		return ResponseEntity.created(location).body(assetDtoSaved.get());
	}

	@GetMapping("/{assetId}/assignments")
	public ResponseEntity<List<AssetAssignmentDto>> getAssetAssignments(@PathVariable Long assetId) {
		Optional<AssetDto> assetDto = assetService.getAssetById(assetId);

		List<AssetAssignmentDto> assetAssignmentsDto = assetService.getAssetAssignments(assetDto.get().getId());
		return ResponseEntity.ok(assetAssignmentsDto);
	}
}
