package com.company.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.company.dto.AssignmentDto;
import com.company.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentRestController {

	private AssignmentService assignmentService;

	@Autowired
	public AssignmentRestController(AssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	@PostMapping("")
	public ResponseEntity<AssignmentDto> getAssignment(@RequestBody AssignmentDto assignmentDto) {

		Optional<AssignmentDto> SavedassignmentDto = assignmentService.saveAssignment(assignmentDto);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(SavedassignmentDto.get().getId()).toUri();
		
		return ResponseEntity.created(location).body(SavedassignmentDto.get());
	}
	
	@PostMapping("/{id}/end")
	public ResponseEntity<LocalDateTime> finishCurrentAssignment(@PathVariable Long id){
		
		Optional<AssignmentDto> assignmentDto = assignmentService.getAssignmentById(id);
		if(assignmentDto.get().getEnd()!=null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "To przypisanie już zostało zakończone");
		}
		
		LocalDateTime EndDate = LocalDateTime.now();
		assignmentDto.get().setEnd(EndDate);
		assignmentService.updateAssignment(assignmentDto.get());
		
		return ResponseEntity.ok(EndDate);
	}
}
