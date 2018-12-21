package com.company.dto;

import com.company.model.User;

public class UserMapper {

	public static UserDto toDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setPesel(user.getPesel());
		return userDto;
	}

	public static User toEntity(UserDto userDto) {
		User user = new User();
		user.setId(userDto.getId());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setPesel(userDto.getPesel());
		return user;
	}
}
