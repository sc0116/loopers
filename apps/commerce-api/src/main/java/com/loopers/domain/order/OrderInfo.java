package com.loopers.domain.order;

import static com.loopers.domain.order.Order.OrderStatus;

import java.math.BigDecimal;

public record OrderInfo(
	Long id,
	Long userId,
	OrderStatus status,
	BigDecimal totalPrice
) {

	public static OrderInfo from(final Order order) {
		return new OrderInfo(
			order.getId(),
			order.getUserId(),
			order.getStatus(),
			order.calculateTotalPrice()
		);
	}
}
