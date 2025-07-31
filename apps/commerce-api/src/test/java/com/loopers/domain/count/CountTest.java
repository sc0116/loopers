package com.loopers.domain.count;

import static com.loopers.domain.count.ProductCount.Count;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class CountTest {

	@DisplayName("카운트를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("카운트가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenCountIsNull(final Long count) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Count(count);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "카운트는 비어있을 수 없습니다."));
		}

		@DisplayName("카운트로 음수가 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenCountIsNegative() {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Count(-1L);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "카운트는 음수일 수 없습니다."));
		}
	}

	@DisplayName("카운트를 감소시킬 때, ")
	@Nested
	class Decrement {

		@DisplayName("현재 카운트가 0인 상황에서 차감하게 되면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwsBadRequestException_whenCountIsZero() {
			final Count sut = new Count(0L);

			final CoreException actual = assertThrows(CoreException.class, sut::decrement);

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "카운트를 감소시킬 수 없습니다."));
		}
	}
}
