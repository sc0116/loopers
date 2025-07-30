package com.loopers.domain.product;

import java.util.List;

public record ProductCommand() {

	public record GetProduct(Long id) {}

	public record GetProducts(List<Long> ids) {}
}
