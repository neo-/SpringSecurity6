package com.example.springsecurity.config.security.filters;

import com.example.springsecurity.config.security.authentication.APIKeyAuthentication;
import com.example.springsecurity.config.security.managers.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

	private final String apiKey;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var headerKey = request.getHeader("x-api-key");
		if ("null".equals(headerKey) || headerKey == null) {
			filterChain.doFilter(request, response);
		} else {
			try {
				var customAuthenticationManager = new CustomAuthenticationManager(apiKey);
				var customAuth = new APIKeyAuthentication(headerKey);
				var a = customAuthenticationManager.authenticate(customAuth);

				if (a.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(a);
					filterChain.doFilter(request, response);
				} else {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
			} catch (AuthenticationException ex) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}
}
