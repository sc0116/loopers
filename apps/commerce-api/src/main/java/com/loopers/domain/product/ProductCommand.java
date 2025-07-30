package com.loopers.domain.product;

public record ProductCommand() {

	public record GetProduct(
		Long id
	) {

	}
}
