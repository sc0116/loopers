package com.loopers.interfaces.api.brand;

import com.loopers.domain.brand.BrandInfo;

public record BrandDto() {

	public record V1() {

		public record GetBrandResponse(Long id, String name, String description) {

			public static GetBrandResponse from(final BrandInfo info) {
				return new GetBrandResponse(info.id(), info.name(), info.description());
			}
		}
	}
}
