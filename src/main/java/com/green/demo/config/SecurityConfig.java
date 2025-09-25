package com.green.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.green.demo.jwt.LoginFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 필수: 암호화에 필요한 클래스
	@Bean
	 public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	 }
	
	private AuthenticationConfiguration authenticationConfiguration;
	
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
		this.authenticationConfiguration = authenticationConfiguration;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
	// 인가작업 설정	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
		http.csrf( (auth) -> auth.disable() ) 			// session 사용시에 활성화로 변경, JWT 비활성화
		    .formLogin( (auth) -> auth.disable() ) 	// formLogin 비활성화
		    .httpBasic( (auth) -> auth.disable() )
		    
		    .authorizeHttpRequests( (auth) -> auth
			.requestMatchers("/login", "/", "/join").permitAll()
			.requestMatchers("/admin").hasRole("ADMIN")
			.anyRequest().authenticated() )
		
		// 1. 커스텀 로그인 필터 등록 LoginFilter 필터추가
		// Before, After, At(기존대체)
		    .addFilterAt( new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class)
		
		// 중요: JWT 세션 설정은 STATELESS 사용
		.sessionManagement( (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) ); 
		
		return http.build();
	}
	
}