package com.green.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.green.demo.dto.JoinDTO;
import com.green.demo.service.JoinService;

@RestController
public class JoinController {

	private final JoinService joinService;
	
	// 생성자 주입 : @Autowired 대신 사용 (추천)
	public JoinController(JoinService joinService) {
		this.joinService = joinService;
	}
	
	@PostMapping("/join") // Insert
	public String addUser(JoinDTO joinDTO) {
		joinService.addUser(joinDTO);
		return "ok";
	}

}