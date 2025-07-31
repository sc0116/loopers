package com.loopers.interfaces.api.product;

import com.loopers.application.product.ProductResult;
import java.math.BigDecimal;
import java.util.List;

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

		public record SearchProductsResponse(List<GetResponse> products, Long totalElements, Integer totalPages) {

			public static SearchProductsResponse from(final ProductResult.SearchProducts searchResults) {
				return new SearchProductsResponse(
					searchResults.products().map(GetResponse::from).toList(),
					searchResults.products().getTotalElements(),
					searchResults.products().getTotalPages()
				);
			}
		}
	}
}
