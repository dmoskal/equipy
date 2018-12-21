package com.company.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.model.Assignment;
import com.company.repository.AssetRepository;
import com.company.repository.UserRepository;

@Component
public class AssignmentMapper {

	private UserRepository userRepository;
	private AssetRepository assetRepository;

	@Autowired
	public AssignmentMapper(UserRepository userRepository, AssetRepository assetRepository) {
		this.userRepository = userRepository;
		this.assetRepository = assetRepository;
	}

	public AssignmentDto toDto(Assignment assignment) {

		AssignmentDto assignmentDto = new AssignmentDto();
		assignmentDto.setId(assignment.getId());
		assignmentDto.setStart(assignment.getStart());
		assignmentDto.setEnd(assignment.getEnd());
		assignmentDto.setUserId(assignment.getUser().getId());
		assignmentDto.setAssetId(assignment.getAsset().getId());
		return assignmentDto;
	}

	public Assignment toEntity(AssignmentDto assignmentDto) {
		Assignment assignment = new Assignment();
		assignment.setId(assignmentDto.getId());
		assignment.setStart(assignmentDto.getStart());
		assignment.setEnd(assignmentDto.getEnd());
		assignment.setAsset(assetRepository.getOne(assignmentDto.getAssetId()));
		assignment.setUser(userRepository.getOne(assignmentDto.getUserId()));
		return assignment;
	}
}
