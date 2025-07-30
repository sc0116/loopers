package com.loopers.domain.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderCommand() {

	public record Order(Long userId, List<OrderItem> items) {

		public record OrderItem(Long productId, Integer quantity, BigDecimal price) {}
	}
}
