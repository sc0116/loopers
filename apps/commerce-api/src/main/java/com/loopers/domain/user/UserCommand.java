package com.loopers.domain.user;

public class UserCommand {

	public record Create(
		String loginId,
		String email,
		String birthDate,
		String gender
	) {
	}
}
