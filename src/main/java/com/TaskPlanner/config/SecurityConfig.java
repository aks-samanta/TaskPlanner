package com.TaskPlanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private static final String[] AUTH_WHITE_LIST = {  };

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().csrf().disable()
				.authorizeHttpRequests()
				.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/v2/api-docs/**",
						"/swagger-resources/**").permitAll()
				.requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
				.anyRequest().authenticated().and()
				.addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
				.httpBasic();
				
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
