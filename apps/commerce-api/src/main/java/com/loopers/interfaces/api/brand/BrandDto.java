package com.loopers.interfaces.api.brand;

import com.loopers.domain.brand.BrandInfo;

public record BrandDto() {

	public record V1() {

		public record GetResponse(
			Long id,
			String name,
			String description
		) {

			public static GetResponse from(final BrandInfo info) {
				return new GetResponse(
					info.id(),
					info.name(),
					info.description()
				);
			}
		}
	}
}
