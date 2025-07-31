package com.loopers.domain.user;

public record UserCommand() {

	public record Register(String loginId, String email, String birthDate, String gender) {}

	public record GetUser(Long id) {}
}
