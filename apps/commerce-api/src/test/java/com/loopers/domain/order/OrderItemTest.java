package com.loopers.domain.order;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderItemTest {

	@DisplayName("주문 상품을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("상품 ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenProductIdIsNull(final Long productId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new OrderItem(productId, 1, BigDecimal.ONE);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "상품 ID는 비어있을 수 없습니다."));
		}

		@DisplayName("수량이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenQuantityIsNull(final Integer quantity) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new OrderItem(1L, quantity, BigDecimal.ONE);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "주문 상품 수량은 비어있을 수 없습니다."));
		}

		@DisplayName("수량으로 1개 미만이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(ints = {-1, 0})
		@ParameterizedTest
		void throwsBadRequestException_whenQuantityIsNotPositive(final Integer quantity) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new OrderItem(1L, quantity, BigDecimal.ONE);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "주문 상품 수량은 최소 1개 이상이어야 합니다."));
		}

		@DisplayName("가격이 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenPriceIsNull(final BigDecimal price) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new OrderItem(1L, 1, price);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "주문 상품 가격은 비어있을 수 없습니다."));
		}

		@DisplayName("가격으로 0원 미만이 주어지면, BAD_REQUEST 예외가 발생한다.")
		@ValueSource(ints = {-2, -1})
		@ParameterizedTest
		void throwsBadRequestException_whenPriceIsNegative(final Integer price) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new OrderItem(1L, 1, BigDecimal.valueOf(price));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "주문 상품 가격은 0원 이상이어야 합니다."));
		}
	}
}
