package com.ecom.userservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<Map<String, Object>> handleExist(ResourceAlreadyExistException ex){
		Map<String, Object> m = Map.of("error","Conflict","message",ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(m);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex){
		Map<String, Object> m = Map.of("error","not found","message",ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(m);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
		Map<String, Object> error = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(err -> error.put(err.getField(), err.getDefaultMessage()));
		Map<String, Object> m = Map.of("error","Validation Failed","details",error);
		return ResponseEntity.badRequest().body(m);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleAll(Exception ex){
		Map<String, Object> m = Map.of("error", "InternalServer Error", "message", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(m);
	}
}
