package com.loopers.domain.brand;

public record BrandInfo(
	Long id,
	String name,
	String description
) {

	public static BrandInfo from(final Brand brand) {
		return new BrandInfo(
			brand.getId(),
			brand.getName(),
			brand.getDescription()
		);
	}
}
