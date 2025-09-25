package com.green.demo.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.green.demo.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	// 해당 아이디(username)가 존재하는지 체크
	Boolean existsByUsername(String username);
	
	// 해당 아이디(username)로 회원정보 검색
	UserEntity findByUsername(String username);
}