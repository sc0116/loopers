package com.loopers.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class UserTest {

	@DisplayName("유저를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("성별에 NULL이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenGenderIsNull() {
			final Gender gender = null;

			final CoreException actual = assertThrows(CoreException.class, () -> {
				new User(
					new LoginId("jjanggu"),
					new Email("jjanggu@gmail.com"),
					new BirthDate("2025-01-01"),
					gender
				);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "성별은 필수입니다."));
		}
	}
}
