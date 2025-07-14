package com.loopers.user.domain;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Table(name = "users")
@Entity
public class User extends BaseEntity {

	private String loginId;

	private String email;

	private String birthDate;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	protected User() {}

	public User(
		final String loginId,
		final String email,
		final String birthDate,
		final Gender gender
	) {
		if (loginId == null || loginId.isBlank()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "ID는 비어있을 수 없습니다.");
		}

		if (!loginId.matches("^[a-zA-Z0-9]+$")) {
			throw new CoreException(ErrorType.BAD_REQUEST, "ID는 영문 및 숫자만 포함되어야 합니다.");
		}

		if (loginId.length() > 10) {
			throw new CoreException(ErrorType.BAD_REQUEST, "ID는 10자를 초과할 수 없습니다.");
		}

		if (email == null || email.isBlank()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "이메일은 비어있을 수 없습니다.");
		}

		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new CoreException(ErrorType.BAD_REQUEST, "이메일 형식이 올바르지 않습니다.");
		}

		if (birthDate == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "생년월일은 필수입니다.");
		}

		try {
			LocalDate.parse(birthDate);
		} catch (DateTimeParseException e) {
			throw new CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다.");
		}

		final LocalDate localDate = LocalDate.parse(birthDate);

		if (localDate.isAfter(LocalDate.now())) {
			throw new CoreException(ErrorType.BAD_REQUEST, "생년월일은 미래일 수 없습니다.");
		}

		if (gender == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "성별은 필수입니다.");
		}

		this.loginId = loginId;
		this.email = email;
		this.birthDate = birthDate;
		this.gender = gender;
	}
}
