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

import com.company.dto.UserAssignmentDto;
import com.company.dto.UserDto;
import com.company.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

	private UserService userService;

	@Autowired
	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("")
	public List<UserDto> getAllUsers(@RequestParam(required = false) String lastName) {
		if (lastName != null && lastName.length() > 0) {
			return userService.getAllByLastName(lastName);
		} else {
			return userService.getAll();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
		Optional<UserDto> userDto = userService.getUserById(id);

		return ResponseEntity.ok().body(userDto.get());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto user){
		if(!id.equals(user.getId()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
			
		Optional<UserDto> userUpdated = userService.update(user);
		
		return ResponseEntity.ok().body(userUpdated.get());
	}

	@PostMapping("")
	public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
		if (user.getId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zapisywany obiekt nie może mieć ustawionego id");
		}

		Optional<UserDto> userDto = userService.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(userDto.get().getId()).toUri();

		return ResponseEntity.created(location).body(userDto.get());
	}
	
	@GetMapping("/{id}/assignments")
	public ResponseEntity<List<UserAssignmentDto>> getAssignmentOfUser(@PathVariable Long id){
		Optional<UserDto> userDto = userService.getUserById(id);
		userDto.ifPresentOrElse(u->{}, ()->{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Aktualizowany obiekt musi mieć id zgodne z id w ścieżce zasobu");
		});
		
		List<UserAssignmentDto> assignmentsDto = userService.getUserAssignments(id);
				
		return ResponseEntity.ok(assignmentsDto);
	}
}
