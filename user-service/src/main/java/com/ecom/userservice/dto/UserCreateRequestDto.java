package com.ecom.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequestDto {
	@NotBlank(message = "name is required")
	private String name;
	@Email(message = "valid email is required")
	@NotBlank(message = "email is required")
	private String email;
	@NotBlank(message = "password is required")
	@Size(min = 6, message = "password must be 6 character length")
	private String password;
	private String phone;
	private String roles;
}
