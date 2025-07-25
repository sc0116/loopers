package com.loopers.domain.point;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AmountTest {

	@DisplayName("금액를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("0 미만의 수가 주어지면, BAD_REQUEST 예외가 반환된다.")
		@ValueSource(longs = {-2L, -1L})
		@ParameterizedTest
		void throwsBadRequestException_whenAmountIsNotPositive(final Long amount) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Amount(amount);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "금액은 0 이상이어야 합니다."));
		}
	}

	@DisplayName("금액를 충전할 때, ")
	@Nested
	class Charge {

		@DisplayName("0 이하의 수가 주어지면, BAD_REQUEST 예외가 반환된다.")
		@ValueSource(longs = {-1L, 0L})
		@ParameterizedTest
		void throwsBadRequestException_whenAmountIsNotPositive(final Long amount) {
			final Amount sut = new Amount(0L);

			final CoreException actual = assertThrows(CoreException.class, () -> {
				sut.charge(amount);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "충전할 금액은 1 이상이어야 합니다."));
		}
	}
}
