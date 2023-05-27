package com.TaskPlanner.config;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {
			SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes());

			String jwt = Jwts.builder()
							.setIssuer("Akash")
							.setSubject("JWT Token")
							.claim("username", auth.getName())
							.claim("role", getRoles(auth.getAuthorities()))
							.setIssuedAt(new Date())
							.setExpiration(new Date(new Date().getTime() + 30000000)).signWith(key).compact();
			
			response.setHeader(SecurityConstants.JWT_HEADER, jwt);
		}
			
		filterChain.doFilter(request, response);
	}

	private String getRoles(Collection<? extends GrantedAuthority> authorities) {

		List<String> roles = authorities.stream().map((ga) -> ga.getAuthority()).collect(Collectors.toList());

		return String.join(",", roles);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		return !(request.getServletPath().equals("/api/users/signIn"));

	}

}
