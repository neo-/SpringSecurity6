package com.example.springsecurity.config.security.providers;

import com.example.springsecurity.config.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Value("${secret.key}")
	private String key;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		CustomAuthentication ca = (CustomAuthentication) authentication;
		if (key.equals(ca.getKey())) {
			ca = new CustomAuthentication();
			ca.setAuthenticated(true);
			return ca;
		}
		throw new BadCredentialsException("Invalid Key!");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomAuthentication.class.equals(authentication);
	}
}
