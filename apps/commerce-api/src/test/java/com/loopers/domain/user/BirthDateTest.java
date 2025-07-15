package com.loopers.domain.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class BirthDateTest {

	@DisplayName("생년월일을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("생년월일에 NULL이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenBirthDateIsNull(final String birthDate) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new BirthDate(birthDate);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "생년월일은 필수입니다."));
		}

		@DisplayName("생년월일이 yyyy-MM-dd 형식이 아니라면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenBirthDateFormatInvalid() {
			final String birthDate = "2025/01/01";

			final CoreException actual = assertThrows(CoreException.class, () -> {
				new BirthDate(birthDate);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다."));
		}

		@DisplayName("생년월일이 현재보다 미래라면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenBirthdateInFuture() {
			final String birthDate = LocalDate.now().plusDays(1).toString();

			final CoreException actual = assertThrows(CoreException.class, () -> {
				new BirthDate(birthDate);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "생년월일은 미래일 수 없습니다."));
		}
	}
}
