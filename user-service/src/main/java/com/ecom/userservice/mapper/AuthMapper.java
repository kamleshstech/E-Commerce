package com.ecom.userservice.mapper;

import org.springframework.stereotype.Component;

import com.ecom.userservice.dto.LoginResponseDto;
import com.ecom.userservice.entity.User;

@Component
public class AuthMapper {
	
	public LoginResponseDto toLoginResponseDto(User user, String token) {
		return new LoginResponseDto(token,"Bearer", user.getId(), user.getEmail(), user.getName());
	}
}
