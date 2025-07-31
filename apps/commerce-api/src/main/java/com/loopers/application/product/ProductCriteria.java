package com.loopers.application.product;

import com.loopers.domain.count.ProductCountCommand;
import com.loopers.domain.product.ProductCommand;
import com.loopers.domain.stock.ProductStockCommand;

public record ProductCriteria() {

	public record GetProduct(Long productId) {

		public ProductCommand.GetProduct toProductCommand() {
			return new ProductCommand.GetProduct(productId);
		}

		public ProductStockCommand.GetStock toStockCommand() {
			return new ProductStockCommand.GetStock(productId);
		}

		public ProductCountCommand.GetProductCount toCountCommand() {
			return new ProductCountCommand.GetProductCount(productId);
		}
	}
}
