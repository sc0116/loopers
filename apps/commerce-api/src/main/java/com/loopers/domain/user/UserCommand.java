package com.loopers.domain.user;

public class UserCommand {

	public record Create(
		String loginId,
		String email,
		String birthDate,
		String gender
	) {

		public User toUser() {
			return new User(
				new LoginId(loginId),
				new Email(email),
				new BirthDate(birthDate),
				Gender.from(gender)
			);
		}
	}
}
