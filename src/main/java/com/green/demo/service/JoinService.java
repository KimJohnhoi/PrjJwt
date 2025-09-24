package com.green.demo.service;

import org.springframework.stereotype.Service;
import com.green.demo.repository.UserRepository;
import com.green.demo.dto.JoinDTO;
import com.green.demo.entity.UserEntity;

@Service
public class JoinService {

	private UserRepository userRepository;
	
	public JoinService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(JoinDTO joinDTO) {
		String username = joinDTO.getUsername();
		String password = joinDTO.getPassword();
		String name = joinDTO.getName();
		
		// 회원정보 있는지 검색
		Boolean isExist = userRepository.existsByUsername(username);
		
		if( isExist ) return;
		
		// 회원정보가 없을 때 실행
		UserEntity user = new UserEntity();
		user.setUsername(username);
		user.setPassword(password);
		user.setRole("ROLE_ADMIN");
		
		userRepository.save(user);
	}
}