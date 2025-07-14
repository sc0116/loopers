package com.loopers.user.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.loopers.support.error.CoreException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

	@DisplayName("유저를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {"", " "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenIdIsNullOrBlank(final String loginId) {
			assertThatThrownBy(() -> new User(loginId, "jjanggu@gmail.com", "2025-01-01", Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("ID는 비어있을 수 없습니다.");
		}

		@DisplayName("ID에 영문 및 숫자 이외의 값이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenIdHasNonAlphanumericChars() {
			final String loginId = "!@#$%한국어";

			assertThatThrownBy(() -> new User(loginId, "jjanggu@gmail.com", "2025-01-01", Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("ID는 영문 및 숫자만 포함되어야 합니다.");
		}

		@DisplayName("ID가 10자 초과하여 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenIdExceedsMaxLength() {
			final String loginId = "12345678910";

			assertThatThrownBy(() -> new User(loginId, "jjanggu@gmail.com", "2025-01-01", Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("ID는 10자를 초과할 수 없습니다.");
		}

		@DisplayName("이메일이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(strings = {"", " "})
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenEmailIsNullOrBlank(final String email) {
			assertThatThrownBy(() -> new User("jjanggu", email, "2025-01-01", Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("이메일은 비어있을 수 없습니다.");
		}

		@DisplayName("이메일이 xx@yy.zz 형식이 아니라면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenEmailFormatInvalid() {
			final String email = "jjanggugmail.com";

			assertThatThrownBy(() -> new User("jjanggu", email, "2025-01-01", Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("이메일 형식이 올바르지 않습니다.");
		}

		@DisplayName("생년월일에 NULL이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenBirthDateIsNull() {
			final String birthDate = null;

			assertThatThrownBy(() -> new User("jjanggu", "jjanggu@gmail.com", birthDate, Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("생년월일은 필수입니다.");
		}

		@DisplayName("생년월일이 yyyy-MM-dd 형식이 아니라면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenBirthDateFormatInvalid() {
			final String birthDate = "2025/01/01";

			assertThatThrownBy(() -> new User("jjanggu", "jjanggu@gmail.com", birthDate, Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("생년월일 형식이 올바르지 않습니다.");
		}

		@DisplayName("생년월일이 현재보다 미래라면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenBirthdateInFuture() {
			final String birthDate = LocalDate.now().plusDays(1).toString();

			assertThatThrownBy(() -> new User("jjanggu", "jjanggu@gmail.com", birthDate, Gender.MALE))
				.isInstanceOf(CoreException.class)
				.hasMessage("생년월일은 미래일 수 없습니다.");
		}

		@DisplayName("성별에 NULL이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenGenderIsNull() {
			final Gender gender = null;

			assertThatThrownBy(() -> new User("jjanggu", "jjanggu@gmail.com", "2025-01-01", gender))
				.isInstanceOf(CoreException.class)
				.hasMessage("성별은 필수입니다.");
		}
	}
}
