package com.loopers.interfaces.api.like;

import com.loopers.application.like.LikeResult;
import java.math.BigDecimal;
import java.util.List;

public record LikeDto() {

	public record V1() {

		public record GetMyProductsResponse(List<GetMyProductResponse> products) {

			public static GetMyProductsResponse from(final LikeResult.GetMyProducts result) {
				final List<GetMyProductResponse> products = result.products().stream()
					.map(GetMyProductResponse::from)
					.toList();

				return new GetMyProductsResponse(products);
			}

			public record GetMyProductResponse(Long id, String name, String description, BigDecimal price) {

				public static GetMyProductResponse from(final LikeResult.GetMyProducts.GetMyProduct result) {
					return new GetMyProductResponse(result.id(), result.name(), result.description(), result.price());
				}
			}
		}
	}
}
