package com.loopers.application.order;

import com.loopers.domain.order.OrderCommand;
import com.loopers.domain.product.ProductCommand;
import com.loopers.domain.product.ProductInfo;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record OrderCriteria() {

	public record Order(Long userId, List<OrderItem> items) {

		public OrderCommand.Order toOrderCommand(final List<ProductInfo> productInfos) {
			final Map<Long, Integer> productQuantities = items.stream()
				.collect(Collectors.toMap(OrderItem::productId, OrderItem::quantity));

			final List<OrderCommand.Order.OrderItem> items = productInfos.stream()
				.map(productInfo -> new OrderCommand.Order.OrderItem(
					productInfo.id(),
					productQuantities.get(productInfo.id()),
					productInfo.price()
				))
				.toList();

			return new OrderCommand.Order(userId, items);
		}

		public ProductCommand.GetProducts toProductCommand() {
			return new ProductCommand.GetProducts(getProductIds());
		}

		private List<Long> getProductIds() {
			return items.stream()
				.map(item -> item.productId)
				.toList();
		}

		public record OrderItem(Long productId, Integer quantity) { }
	}

	public record GetOrder(Long userId, Long orderId) {

		public OrderCommand.GetOrder toCommand() {
			return new OrderCommand.GetOrder(userId, orderId);
		}
	}
}
