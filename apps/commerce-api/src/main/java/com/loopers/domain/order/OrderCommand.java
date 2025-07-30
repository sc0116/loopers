package com.loopers.domain.order;

import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.stock.ProductStockInfo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record OrderCommand() {

	public record Order(Long userId, List<OrderItem> items) {

		public static OrderCommand.Order from(
			final Long userId,
			final List<ProductInfo> productInfos,
			final List<ProductStockInfo> stockInfos
		) {
			final Map<Long, ProductStockInfo> stockInfoMap = stockInfos.stream()
				.collect(Collectors.toMap(ProductStockInfo::productId, Function.identity()));

			final List<OrderItem> items = productInfos.stream()
				.map(productInfo -> {
					final ProductStockInfo stockInfo = stockInfoMap.get(productInfo.id());

					return new OrderItem(
						productInfo.id(),
						stockInfo.fetchQuantity(),
						productInfo.price()
					);
				})
				.toList();

			return new OrderCommand.Order(userId, items);
		}

		public record OrderItem(Long productId, Integer quantity, BigDecimal price) {}
	}
}
