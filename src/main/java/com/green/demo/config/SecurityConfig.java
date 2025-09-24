package com.green.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 필수: 암호화에 필요한 클래스
	@Bean
	 public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	 }
	
	// 인가작업 설정	
	@Bean
	public SecurityFilterChain filterchain(HttpSecurity http) throws Exception{
		http.csrf( (auth) -> auth.disable() ); 			// session 사용시에 활성화로 변경, JWT 비활성화
		http.formLogin( (auth) -> auth.disable() ); 	// formLogin 비활성화
		http.httpBasic( (auth) -> auth.disable() );
		http.authorizeHttpRequests( (auth) -> auth
			.requestMatchers("/login", "/", "/join").permitAll()
			.requestMatchers("/admin").hasRole("ADMIN")
			.anyRequest().authenticated() );
		// 중요: JWT 세션 설정은 STATELESS 사용
		http.sessionManagement( (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) ); 
		
		return http.build();	
	}
	
}