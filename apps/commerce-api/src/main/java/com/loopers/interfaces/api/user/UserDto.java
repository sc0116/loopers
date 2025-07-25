package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserCriteria;
import com.loopers.application.user.UserResult;

public record UserDto() {

	public record V1() {

		public record RegisterRequest(
			String loginId,
			String email,
			String birthDate,
			String gender
		) {

			public UserCriteria.Register toCriteria() {
				return new UserCriteria.Register(loginId, email, birthDate, gender);
			}
		}

		public record GetResponse(
			Long id,
			String loginId,
			String email,
			String birthDate,
			String gender
		) {

			public static GetResponse from(final UserResult userResult) {
				return new GetResponse(
					userResult.id(),
					userResult.loginId(),
					userResult.email(),
					userResult.email(),
					userResult.gender()
				);
			}
		}
	}
}
