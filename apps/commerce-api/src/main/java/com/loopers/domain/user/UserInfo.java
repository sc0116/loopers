package com.loopers.domain.user;

import java.time.LocalDate;

public record UserInfo(
	Long id,
	LoginId loginId,
	Email email,
	BirthDate birthDate,
	Gender gender
) {

	public static UserInfo from(final User user) {
		return new UserInfo(
			user.getId(),
			user.getLoginId(),
			user.getEmail(),
			user.getBirthDate(),
			user.getGender()
		);
	}

	public String fetchLoginId() {
		return loginId.getLoginId();
	}

	public String fetchEmail() {
		return email.getEmail();
	}

	public LocalDate fetchBirthDate() {
		return birthDate.getBirthDate();
	}
}
