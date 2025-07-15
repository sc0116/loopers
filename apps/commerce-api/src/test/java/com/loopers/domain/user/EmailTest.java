package com.loopers.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

	@DisplayName("이메일을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("이메일이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {"", " "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenEmailIsNullOrBlank(final String email) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Email(email);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "이메일은 비어있을 수 없습니다."));
		}

		@DisplayName("이메일이 xx@yy.zz 형식이 아니라면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenEmailFormatInvalid() {
			final String email = "jjanggugmail.com";

			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Email(email);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."));
		}
	}
}
