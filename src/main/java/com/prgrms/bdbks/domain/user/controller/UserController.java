package com.prgrms.bdbks.domain.user.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.bdbks.domain.user.converter.UserMapper;
import com.prgrms.bdbks.domain.user.dto.UserFindResponse;
import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.service.DefaultUserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/users")
@RestController
@RequiredArgsConstructor
public class UserController {
	private final DefaultUserService defaultUserService;
	private final UserMapper userMapper;

	@GetMapping(value = {"/{loginId}"})
	public ResponseEntity<UserFindResponse> readUser(@PathVariable String loginId) {
		Optional<User> user = this.defaultUserService.findUser(loginId);
		return user.map(value -> ResponseEntity.ok(userMapper.entityToFindResponse(value)))
			.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping(value = {"/me"})
	public ResponseEntity<UserFindResponse> getMe(HttpSession session) {
		User user = (User)session.getAttribute("user");
		if (user != null) {
			return ResponseEntity.ok(userMapper.entityToFindResponse(user));
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}