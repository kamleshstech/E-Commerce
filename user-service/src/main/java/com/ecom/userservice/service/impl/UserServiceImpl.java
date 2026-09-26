package com.ecom.userservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.userservice.dto.LoginRequestDto;
import com.ecom.userservice.dto.LoginResponseDto;
import com.ecom.userservice.dto.UserCreateRequestDto;
import com.ecom.userservice.dto.UserResponseDto;
import com.ecom.userservice.entity.User;
import com.ecom.userservice.repository.UserRepository;
import com.ecom.userservice.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository; 
	
	@Override
	public UserResponseDto register(UserCreateRequestDto dto) {
		if(userRepository.existByEmail(dto.getEmail())) {
			throw new RuntimeException("Email is not unique!!");
		}
		User user = new User();
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		User savedUser = userRepository.save(user);
		
		return new UserResponseDto();
	}

	@Override
	public LoginResponseDto login(LoginRequestDto email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
