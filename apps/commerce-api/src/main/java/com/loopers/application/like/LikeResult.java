package com.loopers.application.like;

import com.loopers.domain.product.ProductInfo;
import java.math.BigDecimal;
import java.util.List;

public record LikeResult() {

	public record GetMyProducts(List<GetMyProduct> products) {

		public static GetMyProducts from(final List<ProductInfo> productInfos) {
			final List<GetMyProduct> products = productInfos.stream()
				.map(GetMyProduct::from)
				.toList();

			return new GetMyProducts(products);
		}

		public record GetMyProduct(Long id, String name, String description, BigDecimal price) {

			public static GetMyProduct from(final ProductInfo productInfo) {
				return new GetMyProduct(
					productInfo.id(),
					productInfo.name(),
					productInfo.description(),
					productInfo.price()
				);
			}
		}
	}
}
