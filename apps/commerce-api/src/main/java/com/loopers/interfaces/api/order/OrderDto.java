package com.loopers.interfaces.api.order;

import com.loopers.application.order.OrderCriteria;
import java.util.List;

public record OrderDto() {

	public record V1() {

		public record OrderRequest(List<OrderItemRequest> items) {

			public OrderCriteria.Order toCriteria(final Long userId) {
				final List<OrderCriteria.Order.OrderItem> items = this.items.stream()
					.map(OrderItemRequest::toCriteria)
					.toList();

				return new OrderCriteria.Order(userId, items);
			}

			record OrderItemRequest(Long productId, Integer quantity) {

				private OrderCriteria.Order.OrderItem toCriteria() {
					return new OrderCriteria.Order.OrderItem(productId, quantity);
				}
			}
		}
	}
}
