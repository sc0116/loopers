package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserCriteria;
import com.loopers.application.user.UserResult;

public class UserV1Dto {

	public record UserRegisterRequest(
		String loginId,
		String email,
		String birthDate,
		String gender
	) {

		public UserCriteria.Register toCriteria() {
			return new UserCriteria.Register(loginId, email, birthDate, gender);
		}
	}

	public record UserResponse(
		Long id,
		String loginId,
		String email,
		String birthDate,
		String gender
	) {

		public static UserResponse from(final UserResult userResult) {
			return new UserResponse(
				userResult.id(),
				userResult.loginId(),
				userResult.email(),
				userResult.email(),
				userResult.gender()
			);
		}
	}
}
