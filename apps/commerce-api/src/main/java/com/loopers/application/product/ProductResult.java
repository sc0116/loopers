package com.loopers.application.product;

import com.loopers.domain.brand.BrandInfo;
import com.loopers.domain.count.ProductCountInfo;
import com.loopers.domain.product.ProductInfo;
import com.loopers.domain.stock.ProductStockInfo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

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

	public record SearchProducts(Page<ProductResult> products) {

		public static SearchProducts of(
			final Page<ProductInfo> productInfos,
			final List<ProductStockInfo> stockInfos,
			final List<ProductCountInfo> countInfos,
			final List<BrandInfo> brandInfos
		) {
			final Map<Long, ProductStockInfo> stocks = stockInfos.stream()
				.collect(Collectors.toMap(ProductStockInfo::productId, stock -> stock));
			final Map<Long, ProductCountInfo> counts = countInfos.stream()
				.collect(Collectors.toMap(ProductCountInfo::productId, count -> count));
			final Map<Long, BrandInfo> brands = brandInfos.stream()
				.collect(Collectors.toMap(BrandInfo::id, brand -> brand));

			final Page<ProductResult> products = productInfos
				.map(productInfo -> {
					final Optional<ProductStockInfo> stockInfo = Optional.ofNullable(stocks.get(productInfo.id()));
					final Optional<ProductCountInfo> countInfo = Optional.ofNullable(counts.get(productInfo.id()));
					final BrandInfo brandInfo = brands.get(productInfo.fetchBrandId());

					return ProductResult.of(productInfo, stockInfo, countInfo, brandInfo);
				});

			return new ProductResult.SearchProducts(products);
		}
	}
}
