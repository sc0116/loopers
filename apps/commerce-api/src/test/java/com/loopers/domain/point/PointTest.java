package com.loopers.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PointTest {

	@DisplayName("포인트를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("금액에 0 이하의 수가 주어지면, BAD_REQUEST 예외가 반환된다.")
		@ValueSource(longs = {-1L, 0L})
		@ParameterizedTest
		void throwsBadRequestException_whenAmountIsNotPositive(final Long amount) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Point(1L, amount);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "금액은 양수여야 합니다."));
		}
	}
}
