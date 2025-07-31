package com.loopers.interfaces.api.product;

import com.loopers.application.product.ProductResult;
import java.math.BigDecimal;

public record ProductDto() {

	public record V1() {

		public record GetResponse(
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

			public static GetResponse from(final ProductResult result) {
				return new GetResponse(
					result.id(),
					result.name(),
					result.description(),
					result.price(),
					result.stockQuantity(),
					result.likeCount(),
					result.brandId(),
					result.brandName(),
					result.brandDescription()
				);
			}
		}
	}
}
