package com.loopers.domain.user;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class LoginId {

	private String loginId;

	protected LoginId() {}

	public LoginId(final String loginId) {
		if (loginId == null || loginId.isBlank()) {
			throw new CoreException(ErrorType.BAD_REQUEST, "ID는 비어있을 수 없습니다.");
		}
		if (!loginId.matches("^[a-zA-Z0-9]+$")) {
			throw new CoreException(ErrorType.BAD_REQUEST, "ID는 영문 및 숫자만 포함되어야 합니다.");
		}
		if (loginId.length() > 10) {
			throw new CoreException(ErrorType.BAD_REQUEST, "ID는 10자를 초과할 수 없습니다.");
		}

		this.loginId = loginId;
	}
}
