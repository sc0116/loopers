package com.loopers.application.order;

import com.loopers.domain.product.ProductCommand;
import com.loopers.domain.stock.ProductStockCommand;
import java.util.List;

public record OrderCriteria() {

	public record Order(Long userId, List<OrderItem> items) {

		public ProductCommand.GetProducts toProductCommand() {
			return new ProductCommand.GetProducts(getProductIds());
		}

		public ProductStockCommand.GetStocks toProductStockCommand() {
			return new ProductStockCommand.GetStocks(getProductIds());
		}

		private List<Long> getProductIds() {
			return items.stream()
				.map(item -> item.productId)
				.toList();
		}

		record OrderItem(Long productId, Integer quantity) { }
	}
}
