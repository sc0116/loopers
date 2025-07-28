package com.loopers.domain.brand;

public record BrandCommand() {

	public record Get(
		Long id
	) {

	}
}
