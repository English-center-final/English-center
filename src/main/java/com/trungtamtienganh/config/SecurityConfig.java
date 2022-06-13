package com.trungtamtienganh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.trungtamtienganh.config.jwt.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().and()
//		.sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
				.csrf().disable().authorizeRequests()
				.antMatchers("/admin/exams", "/admin/exams/**").hasAnyRole("EXAM", "ADMIN")
				.antMatchers("/admin/courses", "/admin/courses/**").hasAnyRole("COURSE", "ADMIN")
				.antMatchers("/admin/classes", "/admin/classes/**").hasAnyRole("CLASS", "ADMIN")
				.antMatchers("/admin/schedules", "/admin/schedules/**").hasAnyRole("CLASS", "ADMIN")
				.antMatchers("/admin/**", "/admin/users/**", "/admin/users").hasRole("ADMIN")
				.antMatchers("/user/**").authenticated()
				.anyRequest().permitAll();
//				.and().oauth2Login()
//				// .loginPage("/login-oauth")
//				.authorizationEndpoint().baseUri("/oauth2/authorization")
//				.authorizationRequestRepository(cookieAuthorizationRequestRepository()).and().userInfoEndpoint()
//				.userService(customOAuth2UserService).and().successHandler(oAuth2AuthenticationSuccessHandler)
//				.failureHandler(oAuth2AuthenticationFailureHandler);

		// Thêm một lớp Filter kiểm tra jwt
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}
