package com.ecom.userservice.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecom.userservice.dto.UserCreateRequestDto;
import com.ecom.userservice.dto.UserResponseDto;
import com.ecom.userservice.entity.User;

@Component
public class UserMapper {
	private PasswordEncoder passwordEncoder;
	
	public UserMapper(PasswordEncoder pwdEnco) {
		this.passwordEncoder = pwdEnco;
	}
	
	public User toEntity(UserCreateRequestDto dto) {
		return User.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.password(passwordEncoder.encode(dto.getPassword()))
				.phone(dto.getPhone()) 
				.roles(dto.getRoles())
				.build();
	}
	
	public UserResponseDto toUserRespDto(User user) {
		return UserResponseDto.builder()
				.id(user.getId())
				.name(user.getName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.roles(user.getRoles())
				.createdAt(user.getCreatedAt())
				.updatedAt(user.getUpdatedAt())
				.build();
	}
	
}
