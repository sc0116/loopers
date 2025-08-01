package com.loopers.domain.order;

import static com.loopers.domain.order.Order.OrderStatus;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class OrderServiceIntegrationTest {

	@Autowired
	private OrderService sut;

	@DisplayName("주문을 생성할 때, ")
	@Nested
	class Order {

		@DisplayName("회원 ID와 주문 상품이 주어지면, 주문을 생성한다.")
		@Test
		void createOrder() {
			final OrderCommand.Order command = new OrderCommand.Order(
				1L,
				List.of(new OrderCommand.Order.OrderItem(10L, 10, BigDecimal.valueOf(200)))
			);

			final OrderInfo actual = sut.order(command);

			assertAll(
				() -> assertThat(actual.id()).isNotNull(),
				() -> assertThat(actual.userId()).isEqualTo(1L),
				() -> assertThat(actual.status()).isEqualTo(OrderStatus.PENDING),
				() -> assertThat(actual.totalPrice()).isEqualTo(new BigDecimal("2000"))
			);
		}
	}
}
