package com.example.springsecurity.config.security.filters;

import com.example.springsecurity.config.security.authentication.CustomAuthentication;
import com.example.springsecurity.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private CustomAuthenticationManager customAuthenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		var customAuth = new CustomAuthentication();
		customAuth.setKey(request.getHeader("key"));
		var a = customAuthenticationManager.authenticate(customAuth);

		if (a.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(a);
			filterChain.doFilter(request, response);
		}
	}
}
