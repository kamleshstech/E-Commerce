package com.ecom.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.userservice.dto.LoginRequestDto;
import com.ecom.userservice.dto.LoginResponseDto;
import com.ecom.userservice.dto.UserCreateRequestDto;
import com.ecom.userservice.dto.UserResponseDto;
import com.ecom.userservice.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreateRequestDto dto){
		UserResponseDto respDto = userService.register(dto); 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(respDto);
	}
	@GetMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto){
		LoginResponseDto respDto = userService.login(dto);
		return ResponseEntity.ok(respDto);
	}
	@GetMapping("/{id}") 
	public ResponseEntity<UserResponseDto> getById(@PathVariable Long id, Authentication authentication){
		UserResponseDto resp = userService.getById(id); 
		return ResponseEntity.ok(resp);
	}
}
