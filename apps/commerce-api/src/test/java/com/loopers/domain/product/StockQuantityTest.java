package com.loopers.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class StockQuantityTest {

	@DisplayName("상품 재고 수량을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("재고 수량으로 NULL 값이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenStockQuantityIsNull(final Integer stockQuantity) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new StockQuantity(stockQuantity);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 재고 수량은 비어있을 수 없습니다."));
		}

		@DisplayName("재고 수량으로 0개 미만이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@Test
		void throwBadRequestException_whenStockQuantityIsNegative() {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new StockQuantity(-1);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 재고 수량은 음수가 될 수 없습니다."));
		}
	}
}
