package com.example.springsecurity.config.security.managers;

import com.example.springsecurity.config.security.providers.ApiKeyAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

	private final String key;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		var provider = new ApiKeyAuthenticationProvider(key);
		if (provider.supports(authentication.getClass())) {
			return provider.authenticate(authentication);
		}

		return authentication;
	}
}
