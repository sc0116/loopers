package com.loopers.domain.user;

import com.loopers.domain.BaseEntity;
import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Table(name = "users")
@Entity
public class User extends BaseEntity {

	@Embedded
	private LoginId loginId;

	@Embedded
	private Email email;

	@Embedded
	private BirthDate birthDate;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	protected User() {}

	public User(
		final LoginId loginId,
		final Email email,
		final BirthDate birthDate,
		final Gender gender
	) {
		if (gender == null) {
			throw new CoreException(ErrorType.BAD_REQUEST, "성별은 필수입니다.");
		}

		this.loginId = loginId;
		this.email = email;
		this.birthDate = birthDate;
		this.gender = gender;
	}

	public LoginId getLoginId() {
		return loginId;
	}

	public Email getEmail() {
		return email;
	}
	public BirthDate getBirthDate() {
		return birthDate;
	}

	public Gender getGender() {
		return gender;
	}
}
