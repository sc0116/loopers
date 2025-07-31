package com.loopers.domain.brand;

import java.util.List;

public record BrandCommand() {

	public record GetBrands(List<Long> ids) {}

	public record GetBrand(Long id) {}
}
