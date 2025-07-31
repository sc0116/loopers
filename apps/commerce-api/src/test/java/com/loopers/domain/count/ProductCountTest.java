package com.loopers.domain.count;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class ProductCountTest {

	@DisplayName("상품 카운트를 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("상품 ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenProductIdIsNull(final Long productId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new ProductCount(productId);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 ID는 비어있을 수 없습니다."));
		}
	}
}
