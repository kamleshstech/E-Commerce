package com.ecom.userservice.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.userservice.dto.LoginRequestDto;
import com.ecom.userservice.dto.LoginResponseDto;
import com.ecom.userservice.dto.UserCreateRequestDto;
import com.ecom.userservice.dto.UserResponseDto;
import com.ecom.userservice.entity.User;
import com.ecom.userservice.exception.ResourceAlreadyExistException;
import com.ecom.userservice.exception.ResourceNotFoundException;
import com.ecom.userservice.mapper.AuthMapper;
import com.ecom.userservice.mapper.UserMapper;
import com.ecom.userservice.repository.UserRepository;
import com.ecom.userservice.service.UserService;
import com.ecom.userservice.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepository;
	private UserMapper userMapper;
	private AuthMapper authMapper;
	private PasswordEncoder pwdEncoder;
	private JwtUtil jwtUtil;
	
	@Override
	public UserResponseDto register(UserCreateRequestDto dto) {
		if(userRepository.existsByEmail(dto.getEmail())) { 
			throw new ResourceAlreadyExistException("duplicate Email!! Please provide unique email"); 
		}
		User user = userMapper.toEntity(dto);
		User savedUser = userRepository.save(user);
		
		return userMapper.toUserRespDto(savedUser); 
	}

	@Override
	public LoginResponseDto login(LoginRequestDto dto) {
		User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Invalid Credentials!!"));
		boolean matches = pwdEncoder.matches(dto.getPassword(), user.getPassword()); 
		if(!matches) {
			throw new ResourceNotFoundException("Invalid Credentials");
		}
		String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRoles()); 
		return authMapper.toLoginResponseDto(user, token);
	}

	@Override
	public UserResponseDto getById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found with id : "+id));
		return userMapper.toUserRespDto(user);
	}
}
