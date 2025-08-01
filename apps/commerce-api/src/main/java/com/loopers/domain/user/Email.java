package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
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
}
