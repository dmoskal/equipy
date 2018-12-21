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

import com.company.dto.UserAssignmentDto;
import com.company.dto.UserAssignmentMapper;
import com.company.dto.UserDto;
import com.company.dto.UserMapper;
import com.company.model.User;
import com.company.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private Validator validator;

	@Autowired
	public UserService(UserRepository userRepository, Validator validator) {
		this.userRepository = userRepository;
		this.validator = validator;
	}

	public List<UserDto> getAll() {
		return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
	}

	public List<UserDto> getAllByLastName(String lastName) {
		return userRepository.findAllUserBylastNameContainingIgnoreCase(lastName).stream().map(UserMapper::toDto)
				.collect(Collectors.toList());
	}

	public Optional<UserDto> getUserByPesel(String pesel) {
		Optional<User> userByPesel = userRepository.findUserBypesel(pesel);
		return userByPesel.map(UserMapper::toDto);
	}

	public Optional<UserDto> save(UserDto userDto) {

		validateUserDto(userDto);

		Optional<User> userByPesel = userRepository.findUserBypesel(userDto.getPesel());

		userByPesel.ifPresent(u -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Użytkownik z takim peselem już istnieje");
		});

		User userEntity = UserMapper.toEntity(userDto);
		User userSaved = userRepository.save(userEntity);
		return Optional.of(UserMapper.toDto(userSaved));
	}

	public Optional<UserDto> update(UserDto userDto) {

		validateUserDto(userDto);

		if (userDto.getId() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Użytkownik z id równym null");
		}

		Optional<UserDto> userDtoFromDb = this.getUserById(userDto.getId());

		List<User> usersDiferrentPesel = userRepository.findAllUserBypeselNotLike(userDtoFromDb.get().getPesel());

		for (User u : usersDiferrentPesel) {
			if (userDto.getPesel().equals(u.getPesel())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Użytkownik z takim peselem już istnieje");
			}
		}

		User userSaved = userRepository.save(UserMapper.toEntity(userDto));

		return Optional.of(UserMapper.toDto(userSaved));
	}

	public Optional<UserDto> getUserById(Long id) {
		Optional<User> userGet = userRepository.findById(id);

		if (!userGet.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik o takim id nie istnieje");
		} else {
			return userGet.map(UserMapper::toDto);
		}
	}

	public List<UserAssignmentDto> getUserAssignments(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik o takim id nie istnieje");
		}

		List<UserAssignmentDto> assignmentsDto = user.get().getAssignments().stream().map(UserAssignmentMapper::toDto)
				.collect(Collectors.toList());

		return assignmentsDto;
	}

	private void validateUserDto(UserDto userDto) {
		Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
		validator.validate(userDto, errors);
		if (errors.hasErrors()) {
			StringBuilder message = new StringBuilder();
			for (ObjectError err : errors.getAllErrors()) {
				message.append(err.getDefaultMessage());
			}

			throw new ResponseStatusException(HttpStatus.CONFLICT, message.toString());
		}
	}
}
