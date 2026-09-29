package com.ecom.userservice.service;

import com.ecom.userservice.dto.LoginRequestDto;
import com.ecom.userservice.dto.LoginResponseDto;
import com.ecom.userservice.dto.UserCreateRequestDto;
import com.ecom.userservice.dto.UserResponseDto;

public interface UserService {
	UserResponseDto register(UserCreateRequestDto dto);
	LoginResponseDto login(LoginRequestDto email);
	UserResponseDto getById(Long id);
}
