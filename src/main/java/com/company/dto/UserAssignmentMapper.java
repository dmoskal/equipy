package com.company.dto;

import org.springframework.stereotype.Component;

import com.company.model.Asset;
import com.company.model.Assignment;

@Component
public class UserAssignmentMapper {


	public static UserAssignmentDto toDto(Assignment assignment) {
		UserAssignmentDto userAssignmentDto = new UserAssignmentDto();
		userAssignmentDto.setId(assignment.getId());
		userAssignmentDto.setStart(assignment.getStart());
		userAssignmentDto.setEnd(assignment.getEnd());
		
		Asset asset = assignment.getAsset();
		
		userAssignmentDto.setAssetId(asset.getId());
		userAssignmentDto.setAssetName(asset.getName());
		userAssignmentDto.setAssetSerialNumber(asset.getSerialNumber());
		
		return userAssignmentDto;
	}
	
}
