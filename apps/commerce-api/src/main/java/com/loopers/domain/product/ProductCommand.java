package com.loopers.domain.product;

public record ProductCommand() {

	public record Get(
		Long id
	) {

	}
}
