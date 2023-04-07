package com.example.springsecurity.config;

import com.example.springsecurity.config.security.filters.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Value("${api.key}")
	private String apiKey;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic()
				.and()
				.addFilterAt(new ApiKeyFilter(apiKey), BasicAuthenticationFilter.class)
				.authorizeRequests().anyRequest().authenticated()
				.and().build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		var uds = new InMemoryUserDetailsManager();
		var u1 = User.withUsername("admin")
				.password("12345")
				.authorities("read")
				.build();

		uds.createUser(u1);
		return uds;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
