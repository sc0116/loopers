package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Email {

	private String email;

	protected Email() {}

	public Email(final String email) {
		if (email == null || email.isBlank()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "이메일은 비어있을 수 없습니다.");
		}

		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
			throw new CoreException(ErrorType.BAD_REQUEST, "이메일 형식이 올바르지 않습니다.");
		}

		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Email email1 = (Email) o;
		return Objects.equals(email, email1.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
}
