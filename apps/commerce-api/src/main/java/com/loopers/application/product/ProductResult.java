package com.loopers.application.product;

import com.loopers.domain.brand.BrandInfo;
import com.loopers.domain.count.ProductCountInfo;
import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.stock.ProductStockInfo;
import java.math.BigDecimal;
import java.util.Optional;

public record ProductResult(
	Long id,
	String name,
	String description,
	BigDecimal price,
	Integer stockQuantity,
	Long likeCount,
	Long brandId,
	String brandName,
	String brandDescription
) {

	public static ProductResult of(
		final ProductInfo productInfo,
		final Optional<ProductStockInfo> stockInfo,
		final Optional<ProductCountInfo> countInfo,
		final BrandInfo brandInfo
	) {
		return new ProductResult(
			productInfo.id(),
			productInfo.name(),
			productInfo.description(),
			productInfo.price(),
			stockInfo.map(ProductStockInfo::fetchQuantity).orElse(null),
			countInfo.map(ProductCountInfo::fetchLikeCount).orElse(null),
			brandInfo.id(),
			brandInfo.name(),
			brandInfo.description()
		);
	}
}
