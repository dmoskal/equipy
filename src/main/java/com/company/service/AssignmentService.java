package com.company.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.company.dto.AssetDto;
import com.company.dto.AssetMapper;
import com.company.dto.AssignmentDto;
import com.company.dto.AssignmentMapper;
import com.company.dto.UserDto;
import com.company.dto.UserMapper;
import com.company.model.Assignment;
import com.company.repository.AssignmentRepository;

@Service
public class AssignmentService {

	private AssignmentRepository assignmentRepository;
	private UserService userService;
	private AssetService assetService;
	private AssetMapper assetMapper;
	private AssignmentMapper assignmentMapper;
	
	@Autowired
	public AssignmentService(AssignmentRepository assignmentRepository, UserService userService,
			AssetService assetService,AssetMapper assetMapper,AssignmentMapper assignmentMapper) {
		this.assignmentRepository = assignmentRepository;
		this.userService = userService;
		this.assetService = assetService;
		this.assetMapper = assetMapper;
		this.assignmentMapper=assignmentMapper;
	}

	public Optional<AssignmentDto> getAssignmentById(Long id){
		Optional<Assignment> assignment = assignmentRepository.findById(id);
		
		assignment.ifPresentOrElse(a ->{
			
		}, () ->{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nieznaleziono obketu Assignment o takim id");
		});
		
		return Optional.of(assignment.map(assignmentMapper::toDto).get());
	}
	
	public Optional<AssignmentDto> saveAssignment(AssignmentDto assignmentDto) {

		if(assignmentDto.getUserId()==null || assignmentDto.getAssetId()==null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id użytkownika lub wyposażenia jest rowne null");
		}
		
		Optional<UserDto> userDto = userService.getUserById(assignmentDto.getUserId());
		Optional<AssetDto> assetDto = assetService.getAssetById(assignmentDto.getAssetId());
		
		Optional<Assignment> assignmentCheck = assignmentRepository.findFirstAssignmentByAsset_idAndEndIsNull(assetDto.get().getId());
		assignmentCheck.ifPresent(a ->{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wyposażenie o takim id jest już przypisane do innego użytkownika");
		});
		
		Assignment assignment = new Assignment();
		assignment.setStart(LocalDateTime.now());
		assignment.setUser(userDto.map(UserMapper::toEntity).get());
		assignment.setAsset(assetDto.map(assetMapper::toEntity).get());
		
		Assignment assignmentSaved = assignmentRepository.save(assignment);
		
		
		return Optional.of(assignmentMapper.toDto(assignmentSaved));
	}
	
	public Optional<AssignmentDto> updateAssignment(AssignmentDto assignmentDto){
		Assignment assignment = assignmentRepository.save(assignmentMapper.toEntity(assignmentDto));
		Optional<AssignmentDto> assignmentDtoSaved = Optional.of(assignmentMapper.toDto(assignment));
		
		return assignmentDtoSaved;
	}
}
