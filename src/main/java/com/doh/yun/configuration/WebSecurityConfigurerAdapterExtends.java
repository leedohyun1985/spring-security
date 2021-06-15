package com.doh.yun.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigurerAdapterExtends extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/", "/home").permitAll()
		.antMatchers("/hello").hasAnyRole("USER","MANAGER","ADMIN")
		.antMatchers("/manager").hasAnyRole("MANAGER","ADMIN")
		.antMatchers("/admin").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin()
				.loginPage("/login").permitAll().and().logout().permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user1 = User.withDefaultPasswordEncoder()
				.username("user").password("user").roles("USER")
				.build();

		UserDetails user2 = User.withDefaultPasswordEncoder()
				.username("manager").password("manager").roles("MANAGER")
				.build();
		
		UserDetails user3 = User.withDefaultPasswordEncoder()
				.username("admin").password("admin").roles("ADMIN")
				.build();
		List<UserDetails> users = new ArrayList<UserDetails>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		return new InMemoryUserDetailsManager(users);
	}
}
