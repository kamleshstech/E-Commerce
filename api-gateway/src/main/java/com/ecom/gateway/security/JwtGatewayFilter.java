package com.ecom.gateway.security;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequest.Builder;
import org.springframework.web.server.ServerWebExchange;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

public class JwtGatewayFilter implements GatewayFilter, Ordered{
	//1. JwtUtil required 
	//2. Allow Public URLs 
	//3. Check incoming req path has public url then allow without validating form chain filter 
	//4. If auth header is null and not bearer then its unauthorized
	//5. Validate token, get userId, role 
	//6. Add additional info into header like userId, role
	//7. send modified reqs to the controller
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final List<String> PUBLIC_URLS = List.of("/api/users/register","/api/users/login");
	@Override
	public int getOrder() {
		return -1;  //-1 higest priority, 1 medium, anyOther value is lowest
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath(); 
		String method = exchange.getRequest().getMethod().name();
		//if(PUBLIC_URLS.stream().anyMatch(pu -> pu.equals(path))) {  
		if(PUBLIC_URLS.stream().anyMatch(path::contains)) {
			return chain.filter(exchange);
		}
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if(authHeader == null || !authHeader.startsWith("Bearer ")) { 
			return Unauthorized(exchange);
		}
		String token = authHeader.substring(7);
		if(!jwtUtil.validateToken(token)) { 
			return Unauthorized(exchange);
		}
		String userId = jwtUtil.getUserId(token);
		String role = jwtUtil.getRoles(token);
		if(!isAuthorized(role, path, method)) {
			return forbidden(exchange);
		}
		ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().header("X-User-Id", userId, "X-User-Role", role).build(); 
		
		return chain.filter(exchange.mutate().request(modifiedRequest).build()); 
	}
	
	private Mono<Void> Unauthorized(ServerWebExchange exchange){
		exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
		return exchange.getResponse().setComplete();
	}
	private static final Map<String, Map<String, List<String>>> ROLE_API_PERMISSION = Map.of(
				"ADMIN", Map.of("GET", List.of("/api/product"),
						        "POST", List.of("/api/product"),
						        "PUT", List.of("/api/product"),
						        "DELETE", List.of("/api/product")),
				"USER", Map.of("GET", List.of("/api/product"))
			);    
	private boolean isAuthorized(String role, String path, String method) {
		Map<String, List<String>> allowedPermission = ROLE_API_PERMISSION.get(role);
		if(allowedPermission == null) {
			return false;
		}	
		List<String> allowedPaths = allowedPermission.get(method);
		if(allowedPaths == null) {
			return false;
		}
		return allowedPaths.stream().anyMatch(path::startsWith); 
	}
	private Mono<Void> forbidden(ServerWebExchange exchange) {
		exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
		return exchange.getResponse().setComplete();
	}
}






























