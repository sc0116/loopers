package com.loopers.interfaces.api.product;

import com.loopers.domain.product.ProductInfo;
import java.math.BigDecimal;

public record ProductDto() {

	public record V1() {

		public record GetResponse(
			Long id,
			Long brandId,
			String name,
			String description,
			BigDecimal price,
			Integer stockQuantity
		) {

			public static GetResponse from(final ProductInfo info) {
				return new GetResponse(
					info.id(),
					info.fetchBrandId(),
					info.name(),
					info.description(),
					info.price(),
					info.fetchStockQuantity()
				);
			}
		}
	}
}
