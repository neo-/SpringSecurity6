package com.example.springsecurity.config;

import com.example.springsecurity.config.security.SecurityAuthorities;
import com.example.springsecurity.config.security.SecurityRole;
import com.example.springsecurity.config.security.filters.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
				.authorizeRequests().anyRequest()
				//.authenticated()
				//.permitAll()
				.hasAuthority(SecurityAuthorities.WRITE)
				.and().build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		var uds = new InMemoryUserDetailsManager();
		var u1 = User.withUsername("bill")
				.password("12345")
				.authorities(
						new SimpleGrantedAuthority(SecurityAuthorities.READ),
						new SimpleGrantedAuthority(SecurityAuthorities.WRITE),
						new SimpleGrantedAuthority(SecurityRole.MANAGER)
				)
				.build();
		var u2 = User.withUsername("jenifer")
				.password("12345")
				.authorities(SecurityAuthorities.READ)
				//.roles(SecurityRole.MANAGER)
				.build();
		var u3 = User.withUsername("john")
				.password("12345")
				.authorities(SecurityAuthorities.READ)
				//.roles(SecurityRole.USER)
				.build();

		uds.createUser(u1);
		uds.createUser(u2);
		uds.createUser(u3);
		return uds;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
