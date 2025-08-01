package com.loopers.interfaces.api.user;

import com.loopers.application.user.UserCriteria;
import com.loopers.application.user.UserResult;
import com.loopers.domain.user.UserInfo;
import java.time.LocalDate;

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

		public record GetUserResponse(
			Long id,
			String loginId,
			String email,
			LocalDate birthDate,
			String gender
		) {

			public static GetUserResponse from(final UserResult userResult) {
				return new GetUserResponse(
					userResult.id(),
					userResult.loginId(),
					userResult.email(),
					userResult.birthDate(),
					userResult.gender()
				);
			}

			public static GetUserResponse from(final UserInfo userInfo) {
				return new GetUserResponse(
					userInfo.id(),
					userInfo.fetchLoginId(),
					userInfo.fetchEmail(),
					userInfo.fetchBirthDate(),
					userInfo.gender().name()
				);
			}
		}
	}
}
