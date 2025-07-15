package com.loopers.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class LoginIdTest {

	@DisplayName("ID를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {"", " "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenIdIsNullOrBlank(final String loginId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new LoginId(loginId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "ID는 비어있을 수 없습니다."));
		}

		@DisplayName("ID에 영문 및 숫자 이외의 값이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenIdHasNonAlphanumericChars() {
			final String loginId = "!@#$%한국어";

			final CoreException actual = assertThrows(CoreException.class, () -> {
				new LoginId(loginId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "ID는 영문 및 숫자만 포함되어야 합니다."));
		}

		@DisplayName("ID가 10자 초과하여 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenIdExceedsMaxLength() {
			final String loginId = "12345678910";

			final CoreException actual = assertThrows(CoreException.class, () -> {
				new LoginId(loginId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "ID는 10자를 초과할 수 없습니다."));
		}
	}
}
