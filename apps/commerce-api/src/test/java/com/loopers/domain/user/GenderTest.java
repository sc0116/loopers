package com.loopers.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class GenderTest {

	@DisplayName("유효하지 않은 값이 주어지면, BAD_REQUEST 예외가 발생한다.")
	@NullAndEmptySource
	@ParameterizedTest
	void throwsBadRequestException_whenGenderFormatInvalid(final String gender) {
		final CoreException actual = assertThrows(CoreException.class, () -> {
			Gender.from(gender);
		});

		assertThat(actual).usingRecursiveComparison()
			.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "성별이 유효하지 않습니다."));
	}
}
