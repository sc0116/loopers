package com.loopers.application.user;

import com.loopers.domain.user.UserInfo;
import java.time.LocalDate;

public record UserResult(
	Long id,
	String loginId,
	String email,
	LocalDate birthDate,
	String gender
) {

	public static UserResult from(final UserInfo userInfo) {
		return new UserResult(
			userInfo.id(),
			userInfo.fetchLoginId(),
			userInfo.fetchEmail(),
			userInfo.fetchBirthDate(),
			userInfo.gender().name()
		);
	}
}
