package com.loopers.domain.order;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;

import com.loopers.support.error.CoreException;
import com.loopers.support.error.ErrorType;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

class OrderTest {

	@DisplayName("주문을 생성할 때, ")
	@Nested
	class Create {

		@DisplayName("회원 ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullSource
		@ParameterizedTest
		void throwsBadRequestException_whenUserIdIsNull(final Long userId) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Order(userId, List.of(new OrderItem(1L, 1, BigDecimal.ONE)));
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "회원 ID는 비어있을 수 없습니다."));
		}

		@DisplayName("회원 ID가 비어있으면, BAD_REQUEST 예외가 발생한다.")
		@NullAndEmptySource
		@ParameterizedTest
		void throwsBadRequestException_whenItemsNullAndEmpty(final List<OrderItem> items) {
			final CoreException actual = assertThrows(CoreException.class, () -> {
				new Order(1L, items);
			});

			assertThat(actual).usingRecursiveComparison()
				.isEqualTo(new CoreException(ErrorType.BAD_REQUEST, "주문 상품은 최소 1개 이상이어야 합니다."));
		}
	}
}
