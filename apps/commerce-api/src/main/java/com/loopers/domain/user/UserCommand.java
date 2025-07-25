package com.loopers.domain.user;

public class UserCommand {

	public record Register(
		String loginId,
		String email,
		String birthDate,
		String gender
	) {
	}
}
