package com.loopers.application.user;

import com.loopers.domain.user.UserCommand;

public class UserCriteria {

	public record Register(
		String loginId,
		String email,
		String birthDate,
		String gender
	) {
		public UserCommand.Create toCommand() {
			return new UserCommand.Create(loginId, email, birthDate, gender);
		}
	}
}
