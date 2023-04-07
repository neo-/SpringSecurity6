package com.example.springsecurity.config.security.providers;

import com.example.springsecurity.config.security.authentication.APIKeyAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

	private final String key;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		var ca = (APIKeyAuthentication) authentication;
		if (key.equals(ca.getApiKey())) {
			ca.setAuthenticated(true);
			return ca;
		}
		throw new BadCredentialsException("Invalid Key!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return APIKeyAuthentication.class.equals(authentication);
	}
}
