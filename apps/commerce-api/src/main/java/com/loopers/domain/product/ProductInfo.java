package com.loopers.domain.product;

import java.math.BigDecimal;

public record ProductInfo(
	Long id,
	BrandId brandId,
	String name,
	String description,
	BigDecimal price,
	StockQuantity stockQuantity
) {

	public static ProductInfo from(final Product product) {
		return new ProductInfo(
			product.getId(),
			product.getBrandId(),
			product.getName(),
			product.getDescription(),
			product.getPrice(),
			product.getStockQuantity()
		);
	}

	public Long fetchBrandId() {
		return brandId.getBrandId();
	}

	public Integer fetchStockQuantity() {
		return stockQuantity.getStockQuantity();
	}
}
