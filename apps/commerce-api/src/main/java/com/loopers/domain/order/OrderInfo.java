package com.loopers.domain.order;

import static com.loopers.domain.order.Order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderInfo(
	Long id,
	Long userId,
	OrderStatus status,
	BigDecimal totalPrice,
	List<OrderItemInfo> items
) {

	public static OrderInfo from(final Order order) {
		final List<OrderItemInfo> items = order.getItems().stream()
			.map(OrderItemInfo::from)
			.toList();

		return new OrderInfo(
			order.getId(),
			order.getUserId(),
			order.getStatus(),
			order.calculateTotalPrice(),
			items
		);
	}

	public List<Long> getProductIds() {
		return items.stream()
			.map(OrderItemInfo::productId)
			.toList();
	}

	public record OrderItemInfo(Long productId, Integer quantity, BigDecimal price) {

		public static OrderItemInfo from(final OrderItem item) {
			return new OrderItemInfo(item.getProductId(), item.getQuantity(), item.getPrice());
		}
	}
}
