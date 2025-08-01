package com.loopers.interfaces.api.order;

import com.loopers.application.order.OrderCriteria;
import com.loopers.application.order.OrderResult;
import com.loopers.domain.order.OrderInfo;
import java.math.BigDecimal;
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

		public record GetOrdersResponse(List<GetOrderResponse> orders) {

			public static GetOrdersResponse from(final List<OrderInfo> orderInfos) {
				final List<GetOrderResponse> orders = orderInfos.stream()
					.map(GetOrderResponse::from)
					.toList();

				return new GetOrdersResponse(orders);
			}

			public record GetOrderResponse(Long id, String status, BigDecimal totalPrice) {

				public static GetOrderResponse from(final OrderInfo orderInfo) {
					return new GetOrderResponse(orderInfo.id(), orderInfo.status().name(), orderInfo.totalPrice());
				}
			}
		}

		public record GetOrderDetailResponse(
			Long id,
			String status,
			BigDecimal totalPrice,
			List<GetOrderItemDetailResponse> items
		) {

			public static GetOrderDetailResponse from(final OrderResult result) {
				final List<GetOrderItemDetailResponse> itemDetails = result.items().stream()
					.map(item -> new GetOrderItemDetailResponse(
						item.productId(),
						item.name(),
						item.quantity(),
						item.price()
					))
					.toList();

				return new GetOrderDetailResponse(
					result.id(),
					result.status().name(),
					result.totalPrice(),
					itemDetails
				);
			}

			public record GetOrderItemDetailResponse(Long productId, String name, Integer quantity, BigDecimal price) {}
		}
	}
}
