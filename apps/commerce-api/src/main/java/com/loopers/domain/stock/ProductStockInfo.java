package com.loopers.domain.stock;

public record ProductStockInfo(
	Long productId,
	StockQuantity quantity
) {

	public static ProductStockInfo from(final ProductStock productStock) {
		return new ProductStockInfo(productStock.getProductId(), productStock.getQuantity());
	}

	public Integer fetchQuantity() {
		return quantity.getQuantity();
	}
}
