package com.loopers.application.order;

import com.loopers.domain.order.Order.OrderStatus;
import com.loopers.domain.order.OrderInfo;
import com.loopers.domain.product.ProductInfo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record OrderResult(
	Long id,
	Long userid,
	OrderStatus status,
	BigDecimal totalPrice,
	List<OrderItemResult> items
) {

	public static OrderResult of(final OrderInfo orderInfo, final List<ProductInfo> productInfos) {
		final Map<Long, String> productNames = productInfos.stream()
			.collect(Collectors.toMap(ProductInfo::id, ProductInfo::name));

		final List<OrderItemResult> items = orderInfo.items().stream()
			.map(item -> new OrderItemResult(
				item.productId(),
				productNames.get(item.productId()),
				item.quantity(),
				item.price()
			))
			.toList();

		return new OrderResult(
			orderInfo.id(),
			orderInfo.userId(),
			orderInfo.status(),
			orderInfo.totalPrice(),
			items
		);
	}

	public record OrderItemResult(Long productId, String name, Integer quantity, BigDecimal price) {}
}
