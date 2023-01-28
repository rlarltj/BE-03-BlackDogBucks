package com.prgrms.bdbks.domain.testutil;

import java.time.LocalDate;

import com.prgrms.bdbks.domain.user.entity.User;
import com.prgrms.bdbks.domain.user.role.Role;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserObjectProvider {

	public static User createUser() {
		return User.builder()
			.birthDate(LocalDate.now().minusYears(26L))
			.email("test@naver.com")
			.loginId("test1234")
			.password("password1234")
			.nickname("이디야화이팅")
			.phone("01012341234")
			.role(Role.USER)
			.build();
	}

	public static User createUser(Long id) {
		return User.builder()
			.id(id)
			.birthDate(LocalDate.now().minusYears(26L))
			.email("test@naver.com")
			.loginId("test1234")
			.password("password1234")
			.nickname("이디야화이팅")
			.phone("01012341234")
			.role(Role.USER)
			.build();
	}

}