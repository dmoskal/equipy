package com.company.dto;

import com.company.model.Assignment;
import com.company.model.User;

public class AssetAssignmentsMapper {

	public static AssetAssignmentDto toDto(Assignment assignment) {
		AssetAssignmentDto assetAssignmentDto = new AssetAssignmentDto();
		assetAssignmentDto.setId(assignment.getId());
		assetAssignmentDto.setStart(assignment.getStart());
		assetAssignmentDto.setEnd(assignment.getEnd());

		User user = assignment.getUser();
		assetAssignmentDto.setUserId(user.getId());
		assetAssignmentDto.setFirstName(user.getFirstName());
		assetAssignmentDto.setLastName(user.getLastName());
		assetAssignmentDto.setPesel(user.getPesel());
		
		return assetAssignmentDto;
	}
}
