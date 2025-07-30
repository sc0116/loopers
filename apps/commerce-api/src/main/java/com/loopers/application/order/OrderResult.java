package com.loopers.application.order;

import com.loopers.domain.order.Order.OrderStatus;
import com.loopers.domain.order.OrderInfo;
import java.math.BigDecimal;

public record OrderResult(
	Long id,
	Long userid,
	OrderStatus status,
	BigDecimal totalPrice
) {

	public static OrderResult from(final OrderInfo orderInfo) {
		return new OrderResult(
			orderInfo.id(),
			orderInfo.userId(),
			orderInfo.status(),
			orderInfo.totalPrice()
		);
	}
}
