package com.ecom.userservice.exception;

public class ResourceAlreadyExistException extends RuntimeException{
	
	public ResourceAlreadyExistException(String message) {
		super(message);
	}
}
